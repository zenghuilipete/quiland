package creative.fire.concurrent.synchronizer;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Barrier 路障
 * 它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 * 
 * 门闩用来等待事件，路障用来等待其他线程。
 * 门闩当计数减到0时，执行await后的代码；路障是当await数量到达设定数量后，才继续往下执行。
 */
public class CyclicBarrierTest {
	class CyclicBarrierTask implements Callable<Long> {
		private CyclicBarrier barrier;
		private final Integer sequence;

		public CyclicBarrierTask(Integer sequence, CyclicBarrier barrier) {
			this.sequence = sequence;
			this.barrier = barrier;
		}

		@Override
		public Long call() {
			try {
				System.out.println(sequence + " 等待进入...");
				barrier.await();
				doWork();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			return System.nanoTime();
		}

		void doWork() throws InterruptedException {
			System.out.println("工人" + sequence + " 进来了, 开始工作");
			Thread.sleep(500);
			System.out.println("工人" + sequence + " 完成工作");
		}
	}

	public double timeTasks(int times) throws InterruptedException {
		final CyclicBarrier barrier = new CyclicBarrier(times, new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println("开门， 大家一起进入");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		long start = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(times);
		ArrayList<Future<Long>> taskList = new ArrayList<Future<Long>>();
		for (int i = 0; i < times; i++) {
			Future<Long> f = exec.submit(new CyclicBarrierTask(i, barrier));
			taskList.add(f);
		}
		exec.shutdown();

		for (int i = 0; i < times; i++) {
			try {
				Long sequence = taskList.get(i).get();
				System.err.println("工人" + i + " 离开: " + sequence);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		long end = System.currentTimeMillis();
		return (end - start) / 1000d;
	}

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		int times = Runtime.getRuntime().availableProcessors();
		System.out.println("available processors = " + times);
		if (times < 4) {
			times = 4;
		}
		double elapsed = new CyclicBarrierTest().timeTasks(times);
		System.out.println("耗时：" + elapsed + " 秒");
	}
}