package creative.fire.tls.ssl;

import static creative.fire.tls.TLSParameter.ICM_KEYSTORE;
import static creative.fire.tls.TLSParameter.PROTOCOL;
import static creative.fire.tls.TLSParameter.SHA1PRNG;
import static creative.fire.tls.TLSParameter.SSLPORT;
import static creative.fire.tls.TLSParameter.keyStoreType;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;

import creative.fire.tls.tools.PConsole;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class SSLServer1 {
	public static void main(String args[]) throws Exception {
		SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
		KeyManager[] km = TLSSocket.createKeyManagers(keyStoreType, ICM_KEYSTORE);
		TrustManager[] tm = TLSSocket.createTrustManagers(keyStoreType, ICM_KEYSTORE);
		SecureRandom random = SecureRandom.getInstance(SHA1PRNG);
		sslContext.init(km, tm, random);
		
		SSLServerSocketFactory factory = sslContext.getServerSocketFactory();		
		ServerSocket ss = (SSLServerSocket) factory.createServerSocket(SSLPORT);
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
