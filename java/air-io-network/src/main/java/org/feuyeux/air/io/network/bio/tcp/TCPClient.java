package org.feuyeux.air.io.network.bio.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
	private final static Logger logger = LogManager.getLogger(TCPClient.class);
	final Socket socket;
	final String host;
	final int port;
	final BufferedReader reader;
	final PrintWriter writer;

	public TCPClient(String host, int port) throws IOException, InterruptedException {
		super();
		this.host = host;
		this.port = port;
		socket = new Socket(this.host, this.port);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream(), true);
	}

	public String sendMessage(String message) throws IOException {
		if (message.isEmpty()) {
			return "";
		}
		String result = null;
		try {
			writer.println(message);
			writer.flush();
			return result = reader.readLine();
		} catch (Exception e) {
			logger.error(e);
			return "";
		} finally {
			if (result == null) {
				close();
			}
		}
	}

	public void readIn() throws IOException {
		boolean flag = true;
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
		while (flag) {
			String command = systemIn.readLine();
			if (command == null || AirIO.QUIT.equalsIgnoreCase(command.trim())) {
				flag = false;
				close();
				continue;
			}
			String result = sendMessage(command);
			if (!result.isEmpty()) {
				logger.info(result);
			}
		}
	}

	public void close() throws IOException {
		logger.info("Client quit!");
		writer.println(AirIO.QUIT);
		writer.close();
		reader.close();
		socket.close();
	}

	public static void main(String[] args) throws Exception {
		TCPClient client = new TCPClient(AirIO.SERVER_IP, AirIO.BIO_TCP_PORT);
		client.readIn();
	}
}
