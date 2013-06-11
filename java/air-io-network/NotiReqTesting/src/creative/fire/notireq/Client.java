package creative.fire.notireq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket(IO.SERVER_IP, IO.BIO_TCP_PORT);
			readLine(socket);
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		} catch (InterruptedException e) {			
		}
	}

	private static void readLine(Socket socket) throws IOException, InterruptedException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		boolean flag = true;
		while (flag) {
			String command = in.readLine();
			if (command == null) {
				flag = true;
				continue;
			} else {
				//Thread.sleep(2000);
				out.println(command);
				out.flush();
				out.close();
				in.close();
				socket.close();
				flag = false;
			}
		}
	}
}