package creative.air.io.tcpbio.multithread;

/**
 * @author feuyeux@gmail.com
 * 2011-5-12
 */

import java.io.OutputStream;
import java.net.Socket;

import creative.air.io.IO;

public class Client {
	private Socket socket = null;
	private long millis = 5000;
	private int clientId;

	public Client(int clientId) {
		this.clientId = clientId;
	}

	public void work() {
		System.out.println("Client" + clientId + ":start");
		try {
			socket = new Socket(IO.SERVER_IP, IO.BIO_TCP_PORT);
			new Connect("Client" + clientId + "reader", socket).start();

			while (true) {
				String sendMessage = "Client" + clientId + Message.KEEPALIVE + "\n";
				OutputStream socketOut = socket.getOutputStream();
				socketOut.write(sendMessage.getBytes());
				socketOut.flush();
				Thread.sleep(millis);
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}