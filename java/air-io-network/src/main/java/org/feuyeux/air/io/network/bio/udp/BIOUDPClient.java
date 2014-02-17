package org.feuyeux.air.io.network.bio.udp;

import org.feuyeux.air.io.network.common.ENV;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public final class BIOUDPClient {
    private static DatagramSocket serverSocket;

    public static void main(String[] args) throws Exception {
        serverSocket = new DatagramSocket(ENV.BIO_UDP_PORT_0);
        byte[] buffer = new byte[ENV.BYTE_SIZE_2K];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        DatagramSocket socket = new DatagramSocket();
        InetAddress server = InetAddress.getByName("localhost");
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        while (flag) {
            String command = systemIn.readLine();
            if (command == null || ENV.QUIT.equalsIgnoreCase(command.trim())) {
                flag = false;
                System.out.println("BIOUDPClient quit!");
                socket.close();
                continue;
            }
            byte[] datas = command.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(datas, datas.length, server, ENV.BIO_UDP_PORT_1);
            socket.send(packet);
            serverSocket.receive(receivePacket);
            String receiveResponse = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
            System.out.println(receiveResponse);
        }
    }
}
