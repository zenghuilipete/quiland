package creative.fire.tls.ssl.unidirection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import creative.fire.tls.TLSParameter;
import creative.fire.tls.tools.PConsole;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class SSLClient {

	public static void main(String args[]) throws Exception {
		System.setProperty("javax.net.ssl.trustStore", TLSParameter.UCGW_KEYSTORE);
		SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket s = (SSLSocket) ssf.createSocket(InetAddress.getLocalHost().getHostAddress(), TLSParameter.SSLPORT);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String x = in.readLine();
		PConsole.print(x);
		in.close();
	}
}