package org.feuyeux.air.io.network.bio.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;

public class TCPServerThread implements Runnable {
	private final static Logger logger = Logger.getLogger(TCPServerThread.class);

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	TCPServerThread(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {
			while (true) {
				String line = in.readLine();
				if (line == null) {
					Thread.sleep(100);
					continue;
				}
				if (AirIO.QUIT.equalsIgnoreCase(line.trim())) {
					in.close();
					out.close();
					logger.info("Socket Server Thread has been shutdown!");
					Thread.sleep(100);
					System.exit(0);
				} else {
					logger.info("Message from client: " + line);
					out.println("Server response： " + line);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				socket.close();
			} catch (Exception e2) {
				System.err.println("socket can not closed.");
			}
		}
	}
}
