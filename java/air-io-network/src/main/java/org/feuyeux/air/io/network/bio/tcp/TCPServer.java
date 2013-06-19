package org.feuyeux.air.io.network.bio.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;

public class TCPServer {
	private final static Logger logger = Logger.getLogger(TCPServer.class);

	final ServerSocket serverSocket;
	final int port;
	private ExecutorService e;

	public TCPServer(int port) throws IOException {
		super();
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		e = Executors.newFixedThreadPool(AirIO.FixThreadNumber);
		start();
	}

	private void start() throws IOException {
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				logger.info("the remote port connected: " + socket.getPort());
				try {
					Runnable r = new TCPServerThread(socket);
					e.execute(r);
				} catch (Exception e) {
					socket.close();
				}
			}
		} finally {
			serverSocket.close();
		}
	}
}
