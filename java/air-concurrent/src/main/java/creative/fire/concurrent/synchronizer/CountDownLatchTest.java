package creative.fire.concurrent.synchronizer;

import java.util.concurrent.*;

public class CountDownLatchTest {
	class Worker implements Runnable {
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;
		private final String name;

		Worker(String name, CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.name = name;
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		public void run() {
			try {
				startSignal.await();
				doWork();
				doneSignal.countDown();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}

		void doWork() {
			System.out.println(name + " 进来了");
		}
	}

	public long timeTasks(int times) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(times);

		for (int i = 0; i < times; i++) {
			new Thread(new Worker("第" + i + "个人", startGate, endGate)).start();
		}

		long start = System.nanoTime();

		System.out.println("开门");
		startGate.countDown();

		endGate.await();
		System.out.println("关门");

		long end = System.nanoTime();
		return end - start;
	}

	public static void main(String[] ss) throws InterruptedException {
		long elapsed = new CountDownLatchTest().timeTasks(10);
		System.out.println("耗时：" + elapsed + " 毫秒");
	}
}
