package creative.fire.tls.network;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WindowsNetworkInfo extends NetworkInfo {

	public static final String IPCONFIG_COMMAND = "ipconfig /all";

	public String parseMacAddress() throws Exception {
		// run command
		String ipConfigResponse = null;
		ipConfigResponse = runConsoleCommand(IPCONFIG_COMMAND);

		// get localhost address
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;

		while (lastMacAddress == null && tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();

			// see if line might contain a MAC address
			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0) {
				continue;
			}

			// trim the line and see if it matches the pattern
			String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
			if (isMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}

		if (lastMacAddress == null) {
			throw new Exception("Cannot read MAC address from [" + ipConfigResponse + "]");
		}

		return trim(lastMacAddress);
	}

	private static boolean isMacAddress(String macAddressCandidate) {
		Pattern macPattern = Pattern.compile("[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}");
		Matcher m = macPattern.matcher(macAddressCandidate);
		return m.matches();
	}
}