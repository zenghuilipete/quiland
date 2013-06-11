package creative.air.io.tcpbio.multithread;

import java.util.concurrent.CountDownLatch;

import creative.air.io.IO;

public class ClientTest {
	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(1);

		for (int i = 0; i < IO.COUNT; i++) {
			final Client client = new Client(i);
			Thread t = new Thread("Client" + i) {
				@Override
				public void run() {
					try {
						latch.await();
						client.work();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
		//System.out.println(latch.getCount());
		latch.countDown();
		//System.out.println(latch.getCount());
	}
}
