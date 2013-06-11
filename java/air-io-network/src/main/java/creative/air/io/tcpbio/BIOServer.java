package creative.air.io.tcpbio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import creative.air.io.IO;

public class BIOServer {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(IO.BIO_TCP_PORT);
		System.out.println("Server listen on port: " + IO.BIO_TCP_PORT);
		readLine(serverSocket);
		//readBuffer(serverSocket);
	}

	@SuppressWarnings("unused")
	private static void readBuffer(ServerSocket serverSocket) throws IOException, InterruptedException {
		byte[] buffer = new byte[1024];
		BufferedInputStream input = null;
		ByteArrayOutputStream serverOutput = new ByteArrayOutputStream();

		while (true) {
			Socket socket = serverSocket.accept();
			//socket.setTcpNoDelay(on);
			//socket.setSoLinger(on, linger)
			InputStream inputStream = socket.getInputStream();

			int count = 0;
			input = new BufferedInputStream(inputStream);
			if ((count = input.read(buffer)) != -1) {
				serverOutput.write(buffer, 0, count);

				OutputStream outputStream = socket.getOutputStream();
				String message = "Server response " + System.getProperty("line.separator") + serverOutput.toString();
				outputStream.write(message.getBytes());
				outputStream.flush();

				System.out.println("Message from client  " + serverOutput.toString());
				serverOutput.reset();
			} else {
				Thread.sleep(1000);
			}
		}
	}

	private static void readLine(ServerSocket serverSocket) throws IOException, InterruptedException {
		BufferedReader in;
		PrintWriter out;
		while (true) {
			Socket socket = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			String line = in.readLine();
			if (line == null) {
				Thread.sleep(100);
				continue;
			}
			if ("quit".equalsIgnoreCase(line.trim())) {
				in.close();
				out.close();
				serverSocket.close();
				System.out.println("Server has been shutdown!");
				System.exit(0);
			} else {
				System.out.println("Message from client: " + line);
				out.println("Server response：" + line);
				Thread.sleep(100);
			}
		}
	}

}
