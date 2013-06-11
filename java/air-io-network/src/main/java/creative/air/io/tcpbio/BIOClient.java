package creative.air.io.tcpbio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import creative.air.io.IO;

public class BIOClient {
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket(IO.SERVER_IP, IO.BIO_TCP_PORT);
			readLine(socket);
			// send(socket);
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
	}

	@SuppressWarnings("unused")
	private static void send(Socket socket) {
		byte[] buffer = new byte[1024];
		try {
			OutputStream outputStream = socket.getOutputStream();
			buffer = "Hello world!".getBytes();
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readLine(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		while (flag) {
			String command = systemIn.readLine();// read line
			if (command == null || "quit".equalsIgnoreCase(command.trim())) {
				flag = false;
				System.out.println("Client quit!");
				out.println("quit");
				out.close();
				in.close();
				socket.close();
				continue;
			}
			out.println(command);
			String response = in.readLine();
			System.out.println(response);
		}
	}

	@SuppressWarnings("unused")
	private static void readBuffer(Socket socket) throws IOException, InterruptedException {
		byte[] buffer = new byte[1024];
		BufferedInputStream input = null;
		ByteArrayOutputStream serverOutput = new ByteArrayOutputStream();

		while (true) {
			InputStream inputStream = socket.getInputStream();
			int count = 0;
			input = new BufferedInputStream(inputStream);
			count = input.read(buffer);
			if (count != -1) {
				serverOutput.write(buffer, 0, count);
				System.out.println(serverOutput);
				inputStream.close();
				socket.close();
			} else {
				Thread.sleep(1000);
			}
		}
	}
}
