package creative.fire.concurrent.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
	public static void main(String[] args) {
		final int corePoolSize = 2;
		final int maximumPoolSize = 4;
		final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(1);

		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 1, TimeUnit.DAYS, queue);
		for (int i = 20; i > 0; i--) {
			try {
				executor.execute(new Go(i));
				System.out.println(String.format("thread %s initialized", i));
			} catch (RejectedExecutionException e) {
				System.out.println(i + " in RejectedExecutionException");
			}
		}
		System.out.println("~");

		/*
		 * shutdown() 方法不会阻塞。 
		 * 调用 shutdown() 方法之后，主线程就马上结束了，而线程池会继续运行直到所有任务执行完才会停止。
		 * 如果不调用 shutdown() 方法，那么线程池会一直保持下去，以便随时添加新的任务。
		 */
		executor.shutdown();
	}
}

class Go implements Runnable {
	private String name;

	public Go(int threadName) {
		name = threadName + "";
	}

	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(name + "in InterruptedException");
		}
		System.out.println(String.format("thread %s finished", name));
	}
}