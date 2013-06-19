package org.feuyeux.air.io.network.nio.tcp;

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

import org.apache.log4j.Logger;
import org.feuyeux.air.io.network.AirIO;

public class NIOClient {
	private final static Logger logger = Logger.getLogger(NIOClient.class);
	final Selector selector;
	final SocketChannel channel;
	final String host;
	final int port;
	boolean blocking = false;

	public NIOClient(String host, int port) throws IOException {
		super();
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
				if (command == null || "quit".equalsIgnoreCase(command.trim())) {
					systemIn.close();
					channel.close();
					selector.close();
					logger.info("Client quit!");
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
							int ret = 0;
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
		NIOClient client = new NIOClient(AirIO.SERVER_IP, AirIO.NIO_TCP_PORT);
		client.readIn();
	}
}