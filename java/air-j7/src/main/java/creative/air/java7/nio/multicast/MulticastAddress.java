package creative.air.java7.nio.multicast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.net.SocketException;

/**
 * Parses and represents a multicast address.
 */

class MulticastAddress {
	private final InetAddress group;
	private final int port;
	private final NetworkInterface interf;

	private MulticastAddress(InetAddress group, int port, NetworkInterface interf) {
		this.group = group;
		this.port = port;
		this.interf = interf;
	}

	InetAddress group() {
		return group;
	}

	int port() {
		return port;
	}

	/**
	 * @return The network interface, may be {@code null}
	 */
	NetworkInterface interf() {
		return interf;
	}

	/**
	 * Parses a string of the form "group:port[@interface]", returning a MulticastAddress representing the address
	 */
	static MulticastAddress parse(String s) {
		String[] components = s.split("@");
		if (components.length > 2)
			throw new IllegalArgumentException("At most one '@' expected");

		// get group and port
		String target = components[0];
		int len = components[0].length();
		int colon = components[0].lastIndexOf(':');
		if ((colon < 1) || (colon > (len - 2)))
			throw new IllegalArgumentException("group:port expected");
		String groupString = target.substring(0, colon);
		int port = -1;
		try {
			port = Integer.parseInt(target.substring(colon + 1, len));
		} catch (NumberFormatException x) {
			throw new IllegalArgumentException(x);
		}

		// handle IPv6 literal address
		if (groupString.charAt(0) == '[') {
			len = groupString.length();
			if (groupString.charAt(len - 1) != ']')
				throw new IllegalArgumentException("missing ']'");
			groupString = groupString.substring(1, len - 1);
			if (groupString.length() == 0)
				throw new IllegalArgumentException("missing IPv6 address");
		}

		// get group address
		InetAddress group = null;
		try {
			group = InetAddress.getByName(groupString);
		} catch (UnknownHostException x) {
			throw new IllegalArgumentException(x);
		}
		if (!group.isMulticastAddress()) {
			throw new IllegalArgumentException("'" + group.getHostAddress() + "' is not multicast address");
		}

		// optional interface
		NetworkInterface interf = null;
		if (components.length == 2) {
			try {
				interf = NetworkInterface.getByName(components[1]);
			} catch (SocketException x) {
				throw new IllegalArgumentException(x);
			}
			if (interf == null) {
				throw new IllegalArgumentException("'" + components[1] + "' is not valid interface");
			}
		}
		return new MulticastAddress(group, port, interf);
	}
}
