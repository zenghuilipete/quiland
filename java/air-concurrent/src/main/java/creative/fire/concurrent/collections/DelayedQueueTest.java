package creative.fire.concurrent.collections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class DelayedQueueTest {
	public static class Pair<K, V> {
		public K key;
		public V value;

		public Pair() {
		}

		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	public static class DelayItem<T> implements Delayed {
		private static final long NANO_ORIGIN = System.nanoTime();

		final static long now() {
			return System.nanoTime() - NANO_ORIGIN;
		}

		private static final AtomicLong sequencer = new AtomicLong(0);

		private final long sequenceNumber;

		private final long time;

		private final T item;

		public DelayItem(T submit, long timeout) {
			this.time = now() + timeout;
			this.item = submit;
			this.sequenceNumber = sequencer.getAndIncrement();
		}

		public T getItem() {
			return this.item;
		}

		public long getDelay(TimeUnit unit) {
			long d = unit.convert(time - now(), TimeUnit.NANOSECONDS);
			return d;
		}

		public int compareTo(Delayed other) {
			if (other == this) // compare zero ONLY if same object
				return 0;
			if (other instanceof DelayItem) {
				@SuppressWarnings("rawtypes")
				DelayItem x = (DelayItem) other;
				long diff = time - x.time;
				if (diff < 0)
					return -1;
				else if (diff > 0)
					return 1;
				else if (sequenceNumber < x.sequenceNumber)
					return -1;
				else
					return 1;
			}
			long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
			return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
		}
	}

	public static class Cache<K, V> {
		private ConcurrentMap<K, V> cacheObjMap = new ConcurrentHashMap<K, V>();

		private DelayQueue<DelayItem<Pair<K, V>>> q = new DelayQueue<DelayItem<Pair<K, V>>>();

		private Thread daemonThread;

		public Cache() {

			Runnable daemonTask = new Runnable() {
				public void run() {
					daemonCheck();
				}
			};

			daemonThread = new Thread(daemonTask);
			daemonThread.setDaemon(true);
			daemonThread.setName("Cache Daemon");
			daemonThread.start();
		}

		private void daemonCheck() {

			System.out.println("cache service started.");

			for (;;) {
				try {
					DelayItem<Pair<K, V>> delayItem = q.take();
					if (delayItem != null) {
						// 超时对象处理
						Pair<K, V> pair = delayItem.getItem();
						cacheObjMap.remove(pair.key, pair.value); // compare and remove
					}
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
					System.out.println(e);
					break;
				}
			}

			System.out.println("cache service stopped.");
		}

		// 添加缓存对象
		public void put(K key, V value, long time, TimeUnit unit) {
			V oldValue = cacheObjMap.put(key, value);
			if (oldValue != null)
				q.remove(key);

			long nanoTime = TimeUnit.NANOSECONDS.convert(time, unit);
			q.put(new DelayItem<Pair<K, V>>(new Pair<K, V>(key, value), nanoTime));
		}

		public V get(K key) {
			return cacheObjMap.get(key);
		}

		// 测试入口函数
		public static void main(String[] args) throws Exception {
			Cache<Integer, String> cache = new Cache<Integer, String>();
			cache.put(1, "aaaa", 3, TimeUnit.SECONDS);

			String str = cache.get(1);
			System.out.println(str);

			while (str != null) {

				Thread.sleep(1000);
				{
					System.out.println("sleep 1000 milliseconds");
					str = cache.get(1);
					System.out.println(str);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Cache.main(args);
	}
}
