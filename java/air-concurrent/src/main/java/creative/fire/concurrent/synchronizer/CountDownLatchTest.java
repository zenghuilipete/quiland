package creative.fire.concurrent.synchronizer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Latch 门闩
 * CountDownLatch 的一个有用特性是，它不要求调用 countDown 方法的线程等到计数到达零时才继续，
 * 而在所有线程都能通过之前，它只是阻止任何线程继续通过一个 await。
 * 计数无法被重置。 如果需要重置计数，请考虑使用 CyclicBarrier。 
 * 确保一组特定的活动在某一活动完成前，一直处于等待。比如：
 * 1、在资源R初始化之前，使用该资源的所有活动都处于等待。
 * 2、释放服务S前，完成所有依赖于S的服务。
 * 3、开始多人游戏前，确保所有参与者的终端都连接完毕。
 */
public class CountDownLatchTest {
	class CountDonwLatchTask implements Runnable {
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;
		private final String name;

		CountDonwLatchTask(String name, CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.name = name;
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		@Override
		public void run() {
			try {
				System.out.println(name + " 等待进入...");
				startSignal.await();
				doWork();
				doneSignal.countDown();
				System.out.println(name + " 等待离开...");
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}

		void doWork() throws InterruptedException {
			System.out.println(name + " 进来了, 开始工作>");
			Thread.sleep(500);
			System.out.println(name + " 完成工作<");
		}
	}

	public double timeTasks(int times) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(times);

		long start = System.currentTimeMillis();		
		ExecutorService exec = Executors.newFixedThreadPool(times);
		for (int i = 0; i < times; i++) {
			exec.execute(new CountDonwLatchTask("工人" + i, startGate, endGate));
		}
		exec.shutdown();

//		for (int i = 0; i < times; i++) {
//			new Thread(new CountDonwLatchTask("工人" + i, startGate, endGate)).start();
//		}
		
		Thread.sleep(1000);
		System.out.println("开门");
		startGate.countDown();
		endGate.await();

		Thread.sleep(1000);
		System.out.println("关门");

		long end = System.currentTimeMillis();
		return (end - start) / 1000d;
	}

	public static void main(String[] ss) throws InterruptedException {
		int times = Runtime.getRuntime().availableProcessors();
		System.out.println("available processors = " + times);
		if (times < 4) {
			times = 4;
		}
		double elapsed = new CountDownLatchTest().timeTasks(times);
		System.out.println("耗时：" + elapsed + " 秒");
	}
}
