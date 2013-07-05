package org.feuyeux.air.io.network.bio.tcp.callback;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.feuyeux.air.io.network.AirIO;

public class TCPServer {
	public void startServer(int localPort) {
		try {
			ServerSocket serverSocket = new ServerSocket(localPort);
			System.out.println("TCP Server... on port " + localPort);
			while (true) {
				Socket socket = serverSocket.accept();
				TCPConnection conn = new TCPConnection(socket);
				conn.registerConnListener(new EventHandler("TCP-Server"));
				conn.handleConnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TCPServer server = new TCPServer();
		server.startServer(AirIO.BIO_TCP_PORT);
	}
}
