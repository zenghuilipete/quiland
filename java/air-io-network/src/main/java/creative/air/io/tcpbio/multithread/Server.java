package creative.air.io.tcpbio.multithread;

/**
 * @author feuyeux@gmail.com
 * 2011-5-12
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import creative.air.io.IO;

public class Server {
	private ServerSocket serverSocket;

	public Server() throws IOException {
		serverSocket = new ServerSocket(IO.BIO_TCP_PORT);
		System.out.println("TCP Server Started.");
	}

	public void service() {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("New connection: " + socket.getInetAddress() + ":" + socket.getPort());
				try {
					new Connect("serverReader", socket).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
			} finally {
			}
		}
	}
}