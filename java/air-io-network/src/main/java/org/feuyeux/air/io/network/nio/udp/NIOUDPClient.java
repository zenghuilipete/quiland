package org.feuyeux.air.io.network.nio.udp;

import org.feuyeux.air.io.network.common.ENV;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

public final class NIOUDPClient {
    public static void main(String[] args) throws Exception {
        DatagramChannel receiveChannel = DatagramChannel.open();
        receiveChannel.configureBlocking(false);
        DatagramSocket socket = receiveChannel.socket();
        socket.bind(new InetSocketAddress(ENV.NIO_UDP_PORT_0));
        Selector selector = Selector.open();
        receiveChannel.register(selector, SelectionKey.OP_READ);

        DatagramChannel sendChannel = DatagramChannel.open();
        sendChannel.configureBlocking(false);
        SocketAddress target = new InetSocketAddress(ENV.SERVER_IP, ENV.NIO_UDP_PORT_1);
        sendChannel.connect(target);

        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String command = systemIn.readLine();
            if (command == null || ENV.QUIT.equalsIgnoreCase(command.trim())) {
                systemIn.close();
                sendChannel.close();
                selector.close();
                System.out.println("BIOUDPClient quit!");
                System.exit(0);
            }
            sendChannel.write(Charset.forName("UTF-8").encode(command));
            int nKeys = selector.select(1000);
            if (nKeys > 0) {
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        DatagramChannel dc = (DatagramChannel) key.channel();
                        dc.receive(buffer);
                        buffer.flip();
                        System.out.println(Charset.forName("UTF-8").decode(buffer));
                        buffer = null;
                    }
                }
                selector.selectedKeys().clear();
            }
        }
    }

}
