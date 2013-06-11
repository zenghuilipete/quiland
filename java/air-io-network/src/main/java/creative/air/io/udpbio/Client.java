package creative.air.io.udpbio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import creative.air.io.IO;

public class Client {
	private static DatagramSocket serverSocket;
	public static void main(String[] args) throws Exception {
		serverSocket = new DatagramSocket(IO.BIO_UDP_PORT_0);
		byte[] buffer = new byte[65507];
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
		DatagramSocket socket = new DatagramSocket();
		InetAddress server = InetAddress.getByName("localhost");
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		while (flag) {
			String command = systemIn.readLine();
			if (command == null || "quit".equalsIgnoreCase(command.trim())) {
				flag = false;
				System.out.println("Client quit!");
				socket.close();
				continue;
			} else {
				byte[] datas = command.getBytes("UTF-8");
				DatagramPacket packet = new DatagramPacket(datas, datas.length, server, IO.BIO_UDP_PORT_1);
				socket.send(packet);
			}
			serverSocket.receive(receivePacket);
			String receiveResponse = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
			System.out.println(receiveResponse);
		}
	}
}
