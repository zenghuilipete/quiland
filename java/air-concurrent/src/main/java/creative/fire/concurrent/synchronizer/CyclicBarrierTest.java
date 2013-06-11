package creative.fire.concurrent.synchronizer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		int count = Runtime.getRuntime().availableProcessors();
		System.out.println("available processors = " + count);

		if (count == 1)
			count = 4;

		final CyclicBarrier barrier = new CyclicBarrier(count, new Runnable() {
			@Override
			public void run() {
				System.out.println("figure out what they are doing next.");
			}
		});

		ExecutorService exec = Executors.newFixedThreadPool(count);
		while (count > 0) {
			exec.execute(new Thread(count + "") {
				public void run() {
					System.out.println(getName() + " gets McDonald");
					try {
						barrier.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			Thread.sleep(500);
			count--;
		}
		exec.shutdown();
	}
}