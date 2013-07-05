package creative.fire.notireq;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		final String id = "100";
		ServerSocket serverSocket = new ServerSocket(IO.BIO_TCP_PORT);
		System.out.println("Server is listening on port: " + IO.BIO_TCP_PORT);

		Socket socket = null;
		try {
			socket = serverSocket.accept();
		} catch (Exception e) {
			System.out.println("accept socket error.");
		}

		SendingNotification sender = new SendingNotification(id, socket);
		sender.start();

		ReceivingRequest receiver = new ReceivingRequest(socket);
		receiver.start();
	}
}
