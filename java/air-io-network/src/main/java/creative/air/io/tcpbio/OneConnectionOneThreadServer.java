package creative.air.io.tcpbio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.feuyeux.air.io.network.IO;


public class OneConnectionOneThreadServer {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(IO.BIO_TCP_PORT);
		System.out.println("Server listen on port: " + IO.BIO_TCP_PORT);

		try {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("the remote port connected: " + socket.getPort());
				try {
					Runnable r = new SocketServerThread(socket);
					System.out.format("%s run", r.toString());
				} catch (Exception e) {
					socket.close();
				}
			}
		} finally {
			serverSocket.close();
		}
	}
}

class SocketServerThread extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public SocketServerThread(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		start();
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
				if ("quit".equalsIgnoreCase(line.trim())) {
					in.close();
					out.close();
					System.out.println("Socket Server Thread has been shutdown!");
					System.exit(0);
				} else {
					System.out.println("Message from client: " + line);
					out.println("Server response：" + line);
					Thread.sleep(100);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				socket.close();
			} catch (Exception e2) {
				System.err.println("socket can not closed.");
			}
		}
	}
}
