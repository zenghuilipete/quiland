package creative.air.io.tcpbio.multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author feuyeux@gmail.com 2011-5-13
 */
public class Connect extends Thread {
	private BufferedReader in;
	private Socket socket;

	public Connect(String threadName, Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		setName(threadName);
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
				System.out.println(line);
				if (getName().equals("serverReader")) {
					String response = "answer: " + line + "\n";
					socket.getOutputStream().write(response.getBytes());
					socket.getOutputStream().flush();
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e2) {
				System.err.println("socket can not closed.");
			}
		}
	}
}