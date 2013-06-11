package creative.fire.tls.ssl.bidirection;

import static creative.fire.tls.TLSParameter.temp;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;

import creative.fire.tls.TLSParameter;
import creative.fire.tls.tools.PConsole;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class SSLServer {
	public static void main(String args[]) throws Exception {
		//		final String iviewKS = temp + "iview1.ks";
		//		String serverTrustCert = iviewKS;
		//		String serverCert = iviewKS;

		String serverTrustCert = TLSParameter.ICM_KEYSTORE;
		String serverCert = TLSParameter.ICM_KEYSTORE;

		//bidirection!
		final boolean bidirectional = false;

		System.setProperty("javax.net.ssl.trustStore", serverTrustCert);
		System.setProperty("javax.net.ssl.trustStorePassword", TLSParameter.S_STORE_PASS);

		System.setProperty("javax.net.ssl.keyStore", serverCert);
		System.setProperty("javax.net.ssl.keyStorePassword", TLSParameter.S_STORE_PASS);

		System.setProperty("javax.net.debug", "ssl");
		System.setProperty("https.protocols", "TLSv1");

		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		ServerSocket ss = ssf.createServerSocket(TLSParameter.SSLPORT);

		PConsole.print("SSL Server is started.");
		while (true) {
			SSLSocket s = (SSLSocket) ss.accept();
			s.setNeedClientAuth(bidirectional);
			PrintStream out = new PrintStream(s.getOutputStream());
			out.println("ICM say Hello to UCGW!");
			out.close();
			s.close();
		}
	}
}
