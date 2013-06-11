package creative.fire.tls.network;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinuxNetworkInfo extends NetworkInfo {
	public static final String IPCONFIG_COMMAND = "/sbin/ifconfig";

	public String parseMacAddress() throws Exception {
		String ipConfigResponse = null;
		ipConfigResponse = runConsoleCommand(IPCONFIG_COMMAND);

		String localHost = null;

		localHost = java.net.InetAddress.getLocalHost().getHostAddress();

		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;

		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			boolean containsLocalHost = line.indexOf(localHost) >= 0;

			// see if line contains IP address
			if (containsLocalHost && lastMacAddress != null) {
				return trim(lastMacAddress);
			}

			// see if line contains MAC address
			int macAddressPosition = line.indexOf("HWaddr");
			if (macAddressPosition <= 0) {
				continue;
			}

			String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
			if (isMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}

		throw new Exception("cannot read MAC address for " + localHost + " from [" + ipConfigResponse + "]");
	}

	public String parseDomain(String hostname) {
		return "";
	}

	private final boolean isMacAddress(String macAddressCandidate) {
		Pattern macPattern = Pattern.compile("[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}");
		Matcher m = macPattern.matcher(macAddressCandidate);
		return m.matches();
	}
}