package creative.fire.tls.ssl;

import static creative.fire.tls.TLSParameter.PROTOCOL;
import static creative.fire.tls.TLSParameter.SHA1PRNG;
import static creative.fire.tls.TLSParameter.SSLPORT;
import static creative.fire.tls.TLSParameter.UCGW_KEYSTORE;
import static creative.fire.tls.TLSParameter.keyStoreType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import creative.fire.tls.tools.PConsole;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class SSLClient1 {

	public static void main(String args[]) throws Exception {
		SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
		KeyManager[] km = TLSSocket.createKeyManagers(keyStoreType, UCGW_KEYSTORE);
		TrustManager[] tm = TLSSocket.createTrustManagers(keyStoreType, UCGW_KEYSTORE);
		SecureRandom random = SecureRandom.getInstance(SHA1PRNG);
		sslContext.init(km, tm, random);
		SSLSocketFactory factory = sslContext.getSocketFactory();

		SSLSocket socket = (SSLSocket) factory.createSocket(InetAddress.getLocalHost().getHostAddress(), SSLPORT);
		socket.setUseClientMode(true);

		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String x = in.readLine();
		PConsole.print(x);
		in.close();
	}
}