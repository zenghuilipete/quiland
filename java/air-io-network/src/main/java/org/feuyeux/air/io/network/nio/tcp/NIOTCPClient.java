package org.feuyeux.air.io.network.nio.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NIOTCPClient {
    private static final Logger logger = LogManager.getLogger(NIOTCPClient.class);
    final Selector selector;
    final SocketChannel channel;
    final String host;
    final int port;
    static final boolean blocking = true;

    public NIOTCPClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        SocketAddress target = new InetSocketAddress(this.host, this.port);
        channel.connect(target);
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void readIn() throws IOException {
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            if (channel.isConnected()) {
                String command = systemIn.readLine();
                if (command == null || ENV.QUIT.equalsIgnoreCase(command.trim())) {
                    systemIn.close();
                    channel.close();
                    selector.close();
                    logger.info("BIOUDPClient quit!");
                    System.exit(0);
                } else {
                    channel.write(Charset.forName("UTF-8").encode(command));
                }
            }

            final long timeout = 1000; // milliseconds
            int nKeys = selector.select(timeout);
            if (nKeys > 0) {
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isConnectable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.configureBlocking(blocking); //default true
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        socketChannel.finishConnect();
                    } else if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel sc = (SocketChannel) key.channel();
                        int readBytes = 0;
                        try {
                            int ret;
                            try {
                                while ((ret = sc.read(buffer)) > 0) {
                                    readBytes += ret;
                                }
                            } finally {
                                buffer.flip();
                            }
                            if (readBytes > 0) {
                                logger.info(Charset.forName("UTF-8").decode(buffer).toString());
                                buffer = null;
                            }
                        } finally {
                            if (buffer != null) {
                                buffer.clear();
                            }
                        }
                    }
                }
                selector.selectedKeys().clear();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        NIOTCPClient client = new NIOTCPClient(ENV.SERVER_IP, ENV.NIO_TCP_PORT);
        client.readIn();
    }
}
