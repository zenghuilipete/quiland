package creative.fire.tls.ssl.bidirection;

import static creative.fire.tls.TLSParameter.temp;

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
//		final String ucgwKS = temp + "ucgw1.ks";		
//		String clientTrustCert = ucgwKS;
//		String clientCert = ucgwKS;
		
		String clientTrustCert = TLSParameter.UCGW_KEYSTORE;
		String clientCert = TLSParameter.UCGW_KEYSTORE;
		
		System.setProperty("javax.net.ssl.trustStore", clientTrustCert);
		System.setProperty("javax.net.ssl.trustStorePassword", TLSParameter.S_STORE_PASS);
		System.setProperty("javax.net.ssl.keyStore", clientCert);
		System.setProperty("javax.net.ssl.keyStorePassword", TLSParameter.S_KEY_PASS);

		SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		String ip = InetAddress.getLocalHost().getHostAddress();
		PConsole.print("ip:" + ip + " port:" + TLSParameter.SSLPORT);

		SSLSocket s = (SSLSocket) ssf.createSocket(ip, TLSParameter.SSLPORT);

		s.setSoTimeout(9000);
		s.startHandshake();
				
		if (s.isConnected())
			PConsole.print("connected.");
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String x = in.readLine();
		PConsole.print(x);
		in.close();
	}
}