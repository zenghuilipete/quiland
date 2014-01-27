package org.feuyeux.air.io.network.bio.tcp.callback;

import org.feuyeux.air.io.network.SystemInReadThread;

import java.io.IOException;

public class EventHandler {
	private String connectName;

	public EventHandler(String connectName) {
		super();
		this.connectName = connectName;
	}

	public void onMessageReceived(final TCPConnection conn, final String receivedMsg) {
		System.out.println("<--:" + receivedMsg);

		if ("quit".equals(receivedMsg)) {
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (receivedMsg != null) {
			try {
				conn.sendMessage(receivedMsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void onConnected(final TCPConnection conn) {
		System.out.println("New Connection created:" + conn);
		conn.registerMsg(this);
		new Thread(connectName + "-Handle-msg") {
			@Override
			public void run() {
				conn.receiveMessage();
			}
		}.start();
		new SystemInReadThread(conn).start();
	}
}