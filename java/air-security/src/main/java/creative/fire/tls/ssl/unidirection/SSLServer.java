package creative.fire.tls.ssl.unidirection;

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
		System.setProperty("javax.net.ssl.keyStore", TLSParameter.ICM_KEYSTORE);
		System.setProperty("javax.net.ssl.keyStorePassword", TLSParameter.S_STORE_PASS);
		
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		ServerSocket ss = ssf.createServerSocket(TLSParameter.SSLPORT);
		PConsole.print("SSL Server is started.");
		while (true) {
			Socket s = ss.accept();
			PrintStream out = new PrintStream(s.getOutputStream());
			out.println("ICM say Hello to UCGW!");
			out.close();
			s.close();
		}
	}
}
