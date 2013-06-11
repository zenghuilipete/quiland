package creative.air.io.udpbio;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import creative.air.io.IO;

public class Server {
	private static DatagramSocket client;
	public static void main(String[] args) throws Exception {
		DatagramSocket server = new DatagramSocket(IO.BIO_UDP_PORT_1);
		client = new DatagramSocket();
		InetAddress serverAddress = InetAddress.getByName("localhost");
		byte[] buffer = new byte[65507];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		while (true) {
			server.receive(packet);
			String line = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
			if ("quit".equalsIgnoreCase(line.trim())) {
				server.close();
				System.exit(0);
			} else {
				System.out.println("Message from client: " + line);
				packet.setLength(buffer.length);
				String response = "Server response：" + line;
				byte[] datas = response.getBytes("UTF-8");
				DatagramPacket responsePacket = new DatagramPacket(datas, datas.length, serverAddress, IO.BIO_UDP_PORT_0);
				client.send(responsePacket);
				Thread.sleep(100);
			}
		}
	}
}
