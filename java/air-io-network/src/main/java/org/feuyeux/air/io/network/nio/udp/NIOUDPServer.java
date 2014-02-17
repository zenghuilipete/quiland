package org.feuyeux.air.io.network.nio.udp;

import org.feuyeux.air.io.network.common.ENV;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

public final class NIOUDPServer {
    public static void main(String[] args) throws Exception {
        DatagramChannel sendChannel = DatagramChannel.open();
        sendChannel.configureBlocking(false);
        SocketAddress target = new InetSocketAddress("127.0.0.1", ENV.NIO_UDP_PORT_0);
        sendChannel.connect(target);

        DatagramChannel receiveChannel = DatagramChannel.open();
        DatagramSocket serverSocket = receiveChannel.socket();
        serverSocket.bind(new InetSocketAddress(ENV.NIO_UDP_PORT_1));
        System.out.println("Data receive listen on port: " + ENV.NIO_UDP_PORT_1);
        receiveChannel.configureBlocking(false);
        Selector selector = Selector.open();
        receiveChannel.register(selector, SelectionKey.OP_READ);
        while (true) {
            int nKeys = selector.select(1000);
            if (nKeys > 0) {
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        DatagramChannel dc = (DatagramChannel) key.channel();
                        dc.receive(buffer);
                        buffer.flip();
                        String message = Charset.forName(ENV.UTF_8).decode(buffer).toString();
                        System.out.println("Message from client: " + message);
                        if (ENV.QUIT.equalsIgnoreCase(message.trim())) {
                            dc.close();
                            selector.close();
                            sendChannel.close();
                            System.out.println("BIOUDPServer has been shutdown!");
                            System.exit(0);
                        }
                        String outMessage = "BIOUDPServer response：" + message;
                        sendChannel.write(Charset.forName(ENV.UTF_8).encode(outMessage));
                    }
                }
                selector.selectedKeys().clear();
            }
        }
    }

}
