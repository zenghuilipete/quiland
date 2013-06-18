package creative.fire.notireq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceivingRequest extends Thread {
	private Socket socket;

	public ReceivingRequest(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in;
		boolean finished = false;
		while (!finished) {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = in.readLine();
				if (line == null) {
					Thread.sleep(100);
					continue;
				}
				Helper.getInstance().remove(line);
				in.close();
				socket.close();
				finished = true;
			} catch (Exception e) {
				System.out.println("receive fails to run.");
			}
		}
	}
}
