package org.feuyeux.air.io.network.bio.tcp;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;
import org.junit.Test;

public class TCPClientTest {
	private final static Logger logger = Logger.getLogger(TCPClientTest.class);

	@Test
	public void testTcpCommunication() throws IOException, InterruptedException {
		try {
			TCPClient client = new TCPClient(AirIO.SERVER_IP, AirIO.BIO_TCP_PORT);
			logger.info(client.send("test1"));
		} catch (IOException e) {
			logger.error(e);
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}
}