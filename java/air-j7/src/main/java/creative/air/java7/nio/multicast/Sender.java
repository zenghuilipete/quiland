package creative.air.java7.nio.multicast;

import java.nio.channels.*;
import java.nio.charset.Charset;
import java.net.*;
import java.io.IOException;

/**
 * Sample multicast sender to send a message in a multicast datagram to a given group.
 */

public class Sender {

	private static void usage() {
		System.err.println("usage: java Sender group:port[@interface] message");
		System.exit(-1);
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 2)
			usage();

		MulticastAddress target = MulticastAddress.parse(args[0]);

		// create socket
		ProtocolFamily family = StandardProtocolFamily.INET;
		if (target.group() instanceof Inet6Address)
			family = StandardProtocolFamily.INET6;
		DatagramChannel dc = DatagramChannel.open(family).bind(new InetSocketAddress(0));
		if (target.interf() != null) {
			dc.setOption(StandardSocketOptions.IP_MULTICAST_IF, target.interf());
		}

		// send multicast packet
		dc.send(Charset.defaultCharset().encode(args[1]), new InetSocketAddress(target.group(), target.port()));
		dc.close();
	}

}
