package org.feuyeux.air.io.network.bio.udp;

import org.feuyeux.air.io.network.common.ENV;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BIOUDPServer {
    private static DatagramSocket client;

    public static void main(String[] args) throws Exception {
        DatagramSocket server = new DatagramSocket(ENV.BIO_UDP_PORT_1);
        client = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        byte[] buffer = new byte[ENV.BYTE_SIZE_2K];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (true) {
            server.receive(packet);
            String line = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
            if (ENV.QUIT.equalsIgnoreCase(line.trim())) {
                server.close();
                System.exit(0);
            } else {
                System.out.println("Message from client: " + line);
                packet.setLength(buffer.length);
                String response = "BIOUDPServer response：" + line;
                byte[] datas = response.getBytes("UTF-8");
                DatagramPacket responsePacket = new DatagramPacket(datas, datas.length, serverAddress, ENV.BIO_UDP_PORT_0);
                client.send(responsePacket);
                Thread.sleep(100);
            }
        }
    }
}
