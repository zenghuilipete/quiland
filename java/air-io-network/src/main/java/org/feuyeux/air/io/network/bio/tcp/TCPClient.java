package org.feuyeux.air.io.network.bio.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;

public class TCPClient {
	private final static Logger logger = Logger.getLogger(TCPClient.class);
	final Socket socket;
	final String host;
	final int port;
	final BufferedReader reader;
	final PrintWriter writer;

	public TCPClient(String host, int port) throws IOException, InterruptedException {
		super();
		this.host = host;
		this.port = port;
		this.socket = new Socket(this.host, this.port);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream(), true);
	}

	public String send(String message) throws IOException {
		try {
			writer.println(message);
			return reader.readLine();
		} catch (Exception e) {
			logger.error(e);
			return "";
		} finally {
			logger.info("Client quit!");
			writer.println(AirIO.QUIT);
			writer.close();
			reader.close();
			socket.close();
		}
	}
}
