package creative.fire.notireq;

import java.io.OutputStream;
import java.net.Socket;

public class SendingNotification extends Thread {
	private String id;
	private Socket socket;

	public SendingNotification(String confId_sdId, Socket socket) {
		this.id = confId_sdId;
		this.socket = socket;
	}

	@Override
	public void run() {
		Helper.getInstance().add(id);
		
		OutputStream outputStream = null;
		byte[] buffer = new byte[1024];
		try {
			outputStream = socket.getOutputStream();
			buffer = (id+"\n").getBytes();
			outputStream.write(buffer);
			outputStream.flush();
		} catch (Exception e) {
			System.out.println("don't send success");
			try {
				outputStream.close();
				socket.close();
			} catch (Exception e1) {
			}
		}
	}
}
