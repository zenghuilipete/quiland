package org.feuyeux.air.io.network.bio.tcp;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;
import org.junit.Test;

public class TCPServerTest {
	private final static Logger logger = Logger.getLogger(TCPServerTest.class);

	@Test
	public void testTcpCommunication() throws IOException, InterruptedException {
		try {
			new TCPServer(AirIO.BIO_TCP_PORT);
		} catch (Exception e) {
			logger.info(e);
		}
	}
}
