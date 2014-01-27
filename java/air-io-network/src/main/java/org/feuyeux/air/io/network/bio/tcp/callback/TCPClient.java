package org.feuyeux.air.io.network.bio.tcp.callback;

import org.feuyeux.air.io.network.AirIO;

import java.net.Socket;

public class TCPClient {
	public void startClient(String remoteIP, int remotePort) {
		try {
			System.out.println("TCP Client..., Connecting to server " + remoteIP + ": " + remotePort);
			Socket socket = new Socket(remoteIP, remotePort);
			TCPConnection conn = new TCPConnection(socket);
			conn.registerConnListener(new EventHandler("TCP-Client"));
			conn.handleConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TCPClient client = new TCPClient();
		client.startClient(AirIO.SERVER_IP, AirIO.BIO_TCP_PORT);
	}
}
