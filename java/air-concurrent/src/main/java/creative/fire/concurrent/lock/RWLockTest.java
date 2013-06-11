package creative.fire.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class RWLockTest {
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static WriteLock wLock = lock.writeLock();
	private static ReadLock rLock = lock.readLock();
	private static Map<Integer, String> maps = new HashMap<Integer, String>(2);
	private static CountDownLatch latch = new CountDownLatch(102);
	private static CyclicBarrier barrier = new CyclicBarrier(102);

	static class WriteThread implements Runnable {
		@Override
		public void run() {
			try {
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			wLock.lock();
			maps.put(1, "China");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				wLock.unlock();
			}
			latch.countDown();
		}
	}

	static class ReadThread implements Runnable {
		@Override
		public void run() {
			try {
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}

			rLock.lock();
			maps.get(1);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				rLock.unlock();
			}
			latch.countDown();
		}
	}

	public static void main(String[] args) throws Exception {
		long begin = System.currentTimeMillis();

		for (int i = 0; i < 100; i++) {
			new Thread(new ReadThread()).start();
		}

		for (int i = 0; i < 2; i++) {
			new Thread(new WriteThread()).start();
		}

		latch.await();

		long end = System.currentTimeMillis();

		System.out.println("Consume Time is: " + (end - begin) + " ms");
	}

}
