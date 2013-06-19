package org.feuyeux.air.io.network.bio.tcp;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;
import org.junit.Assert;
import org.junit.Test;

public class TCPCommunicationTest {
	private final static Logger logger = Logger.getLogger(TCPCommunicationTest.class);

	@Test
	public void testTcpCommunication() throws IOException, InterruptedException, ExecutionException {
		final String message = "tcp-bio-test";

		ExecutorService e = Executors.newFixedThreadPool(1);
		e.execute(new Runnable() {
			@Override
			public void run() {
				try {
					new TCPServer(AirIO.BIO_TCP_PORT);
				} catch (Exception e) {
					logger.info(e);
				}
			}
		});

		FutureTask<String> f = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				TCPClient client = new TCPClient(AirIO.SERVER_IP, AirIO.BIO_TCP_PORT);
				return client.sendMessage(message);
			}
		});
		f.run();

		String message2 = f.get();
		logger.info(message2);
		Assert.assertTrue(message2.contains(message));
	}
}