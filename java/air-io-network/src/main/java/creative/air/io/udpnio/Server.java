package creative.air.io.udpnio;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

import creative.air.io.IO;

public class Server {
	public static void main(String[] args) throws Exception {
		DatagramChannel sendChannel = DatagramChannel.open();
		sendChannel.configureBlocking(false);
		SocketAddress target = new InetSocketAddress("127.0.0.1", IO.NIO_UDP_PORT_0);
		sendChannel.connect(target);

		DatagramChannel receiveChannel = DatagramChannel.open();
		DatagramSocket serverSocket = receiveChannel.socket();
		serverSocket.bind(new InetSocketAddress(IO.NIO_UDP_PORT_1));
		System.out.println("Data receive listen on port: " + IO.NIO_UDP_PORT_1);
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
						String message = Charset.forName("UTF-8").decode(buffer).toString();
						System.out.println("Message from client: " + message);
						if ("quit".equalsIgnoreCase(message.trim())) {
							dc.close();
							selector.close();
							sendChannel.close();
							System.out.println("Server has been shutdown!");
							System.exit(0);
						}
						String outMessage = "Server response：" + message;
						sendChannel.write(Charset.forName("UTF-8").encode(outMessage));
					}
				}
				selector.selectedKeys().clear();
			}
		}
	}

}
