package creative.air.io.tcpnio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import creative.air.io.IO;

public class NIOServer {

	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ServerSocket serverSocket = ssc.socket();
		serverSocket.bind(new InetSocketAddress(IO.NIO_TCP_PORT));
		System.out.println("Server listen on port: " + IO.NIO_TCP_PORT);
		ssc.configureBlocking(false);
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			final long timeout = 1000; // milliseconds
			int nKeys = selector.select(timeout);
			if (nKeys > 0) {
				for (SelectionKey key : selector.selectedKeys()) {
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel socketChannel = server.accept();
						if (socketChannel == null) {
							continue;
						}
						System.out.println("the remote port connected: " + socketChannel.socket().getPort());

						boolean blocking = false;
						socketChannel.configureBlocking(blocking); //blocking default value is true

						socketChannel.register(selector, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						SocketChannel channel = (SocketChannel) key.channel();
						int readBytes = 0;
						String message = null;
						try {
							int ret;
							try {
								while ((ret = channel.read(buffer)) > 0) {
									readBytes += ret;
								}
							} catch (Exception e) {
								readBytes = 0;
								// IGNORE
							} finally {
								buffer.flip();
							}
							if (readBytes > 0) {
								message = Charset.forName("UTF-8").decode(buffer).toString();
								buffer = null;
							}
						} finally {
							if (buffer != null) {
								buffer.clear();
							}
						}
						if (readBytes > 0) {
							System.out.println("Message from client: " + message);
							if ("quit".equalsIgnoreCase(message.trim())) {
								channel.close();
								selector.close();
								System.out.println("Server has been shutdown!");
								System.exit(0);
							}
							String outMessage = "Server response: " + message;
							channel.write(Charset.forName("UTF-8").encode(outMessage));
						}
					}
				}
				selector.selectedKeys().clear();
			}
		}
	}
}
