package creative.fire.tls.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import creative.fire.tls.tools.PConsole;

public abstract class NetworkInfo {

	private static final String LOCALHOST = "localhost";

	public static final String NSLOOKUP_CMD = "nslookup";

	public abstract String parseMacAddress() throws Exception;

	/** Not too sure of the ramifications here, but it just doesn't seem right */
	public String parseDomain() throws Exception {
		return parseDomain(LOCALHOST);
	}

	/** Universal entry for retrieving MAC Address */
	public final static String getMacAddress() throws Exception {
		NetworkInfo info = getNetworkInfo();
		String mac = info.parseMacAddress();
		return mac;
	}

	/** Universal entry for retrieving domain info */
	public final static String getNetworkDomain() throws Exception {
		NetworkInfo info = getNetworkInfo();
		String domain = info.parseDomain();
		return domain;
	}

	protected String parseDomain(String hostname) throws Exception {
		// get the address of the host we are looking for - verification
		java.net.InetAddress addy = null;

		try {
			addy = java.net.InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			throw new Exception("Failed to parse network domain", e);
		}

		// back out to the hostname - just validating
		hostname = addy.getCanonicalHostName();
		String nslookupCommand = NSLOOKUP_CMD + " " + hostname;
		// run the lookup command
		String nslookupResponse = null;
		nslookupResponse = runConsoleCommand(nslookupCommand);

		StringTokenizer tokeit = new StringTokenizer(nslookupResponse, "\n", false);
		while (tokeit.hasMoreTokens()) {
			String line = tokeit.nextToken();
			if (line.startsWith("Name:")) {
				line = line.substring(line.indexOf(":") + 1);
				line = line.trim();
				if (isDomain(line, hostname)) {
					line = line.substring(hostname.length() + 1);
					return line;
				}
			}
		}
		return "n.a.";
	}

	private static boolean isDomain(String domainCandidate, String hostname) {
		Pattern domainPattern = Pattern.compile("[\\w-]+\\.[\\w-]+\\.[\\w-]+\\.[\\w-]+");
		Matcher m = domainPattern.matcher(domainCandidate);
		return m.matches() && domainCandidate.startsWith(hostname);
	}

	/** Sort of like a factory... */
	private static NetworkInfo getNetworkInfo() throws Exception {
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows")) {
			return new WindowsNetworkInfo();
		} else if (os.startsWith("Linux")) {
			return new LinuxNetworkInfo();
		} else {
			throw new Exception("unknown system");
		}
	}

	protected String getLocalHost() throws Exception {
		try {
			return java.net.InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException e) {
			throw new Exception("Failed to get local host", e);
		}
	}

	protected String trim(String macAddr) {
		StringBuilder sb = new StringBuilder();
		char ch;
		int trimIndx = 0;

		for (int i = 0; i < macAddr.length(); i++) {
			ch = macAddr.charAt(i);

			if (trimIndx < 2) {
				sb.append(ch);
				trimIndx++;
			} else {
				trimIndx = 0;
			}
		}
		return sb.toString();
	}

	/** Test method */
	public final static void main(String[] args) {
		try {
			PConsole.print("Network infos");
			PConsole.print("  Operating System: " + System.getProperty("os.name"));
			PConsole.print("  IP/Localhost: " + InetAddress.getLocalHost().getHostAddress());
			PConsole.print("  MAC Address: " + getMacAddress());
			PConsole.print("  Domain: " + getNetworkDomain());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	protected String runConsoleCommand(String command) throws Exception {
		String outputText;

		try {
			Process p = Runtime.getRuntime().exec(command);
			InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
			StringBuilder buffer = new StringBuilder();

			for (;;) {
				int c = stdoutStream.read();
				if (c == -1)
					break;
				buffer.append((char) c);
			}

			outputText = buffer.toString();
			stdoutStream.close();
		} catch (IOException e) {
			throw new Exception("Failed to run console command", e);
		}

		return outputText;
	}
}