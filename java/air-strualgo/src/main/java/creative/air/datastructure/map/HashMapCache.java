package creative.air.datastructure.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import creative.air.datastructure.map.AirHashMap.AirEntry;

/**
 * 
 * @author Eric Han feuyeux@gmail.com 06/09/2012
 * @since 0.0.1
 * @version 0.0.1
 */
public class HashMapCache<H, L> {
	enum ConcurrentStrategy {
		NOTIFY, WAIT, TIMEOUT
	}

	enum FullStrategy {
		NOTIFY, DISCARD, REPLACE
	}

	static final Logger logger = Logger.getLogger(HashMapCache.class.getName());
	// cache full strategy
	private int capacity = 12;
	private FullStrategy fullStrategy = FullStrategy.NOTIFY;

	// cache lock strategy
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static WriteLock wLock = lock.writeLock();
	private static ReadLock rLock = lock.readLock();
	private ConcurrentStrategy concurrentStrategy = ConcurrentStrategy.TIMEOUT;
	private long waitingLockTimeout = 500;

	private AirHashMap<H, L> map;

	public HashMapCache() {
		map = new AirHashMap<H, L>();
	}

	public HashMapCache(int capacity) {
		this.capacity = capacity;
		map = new AirHashMap<H, L>();
	}

	public HashMapCache(int capacity, int initialCapacity, float loadFactor) {
		this.capacity = capacity;
		map = new AirHashMap<H, L>(initialCapacity, loadFactor);
	}

	public void clear() {
		try {
			lock(wLock);// tryLock(long timeout, TimeUnit unit)
			map.clear();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "clear error", e);
		} finally {
			wLock.unlock();
		}
	}

	public void remove(H key) {
		try {
			lock(wLock);
			map.remove(key);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "clear error", e);
		} finally {
			wLock.unlock();
		}
	}

	public L put(H key, L value) throws Exception {
		lock(wLock);
		if (this.capacity < map.size()) {
			switch (fullStrategy) {
			case NOTIFY:
				throw new Exception("100 reached the cache's maximum");
			case DISCARD:
				return null;
			case REPLACE:
				// TODO it's a dangerous way
				// which cannot guarantee the data already stored in cache
				AirEntry<H, L> entry = map.getTable()[0];
				remove(entry.getKey());
			default:
				throw new Exception("100 reached the cache's maximum");
			}
		}
		try {
			return map.put(key, value);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "put error", e);
			return null;
		} finally {
			wLock.unlock();
		}
	}

	public L get(H key) {
		try {
			lock(rLock);
			return map.get(key);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "get error", e);
			return null;
		} finally {
			rLock.unlock();
		}
	}

	public Iterator<Map.Entry<H, L>> iterate() {
		try {
			lock(rLock);
			return map.entrySet().iterator();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "get error", e);
			return null;
		} finally {
			rLock.unlock();
		}
	}

	private void lock(Lock lock) throws Exception {
		switch (concurrentStrategy) {
		case NOTIFY:
			throw new Exception("200 Cannot control the cache");
		case WAIT:
			lock.lock();
			break;
		case TIMEOUT:
			lock.tryLock(waitingLockTimeout, TimeUnit.MICROSECONDS);
			break;
		}
	}

	public Set<Map.Entry<H, L>> entrySet() {
		return map.entrySet();
	}

	public int getCapacity() {
		return capacity;
	}

	public ConcurrentStrategy getConcurrentStrategy() {
		return concurrentStrategy;
	}

	public FullStrategy getFullStrategy() {
		return fullStrategy;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setConcurrentStrategy(ConcurrentStrategy concurrentStrategy) {
		this.concurrentStrategy = concurrentStrategy;
	}

	public void setFullStrategy(FullStrategy fullStrategy) {
		this.fullStrategy = fullStrategy;
	}

	public long getWaitingLockTimeout() {
		return waitingLockTimeout;
	}

	public void setWaitingLockTimeout(long waitingLockTimeout) {
		this.waitingLockTimeout = waitingLockTimeout;
	}
}
