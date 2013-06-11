package creative.air.io.tcpnio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import creative.air.io.IO;

public class NIOClient {
	public static void main(String[] args) throws Exception {
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		SocketAddress target = new InetSocketAddress(IO.SERVER_IP, IO.NIO_TCP_PORT);
		channel.connect(target);
		Selector selector = Selector.open();
		channel.register(selector, SelectionKey.OP_CONNECT);
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			if (channel.isConnected()) {
				String command = systemIn.readLine();
				if (command == null || "quit".equalsIgnoreCase(command.trim())) {
					systemIn.close();
					channel.close();
					selector.close();
					System.out.println("Client quit!");
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

						boolean blocking = false;
						socketChannel.configureBlocking(blocking); // blocking default value is
																	// true
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
								System.out.println(Charset.forName("UTF-8").decode(buffer).toString());
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
}
