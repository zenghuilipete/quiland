package creative.fire.tls.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import creative.fire.tls.tools.PConsole;

/**
 * @author feuyeux@gmail.com 2011-8-5
 * //java -Djavax.net.debug=ssl
 */
public class ConnectTest {

	private static char[] KEY_PASS = "radvision".toCharArray();

	public static void main(String args[]) throws Exception {
		String keystore = "D:\\views\\luh_iVIEW_7.7_3_int\\iVIEW\\platform\\jboss-4.0.4.GA\\server\\default\\conf\\ca\\b2bua.keystore";
		String ucgwIp = "192.168.230.160";//"192.168.227.227";
		int sslPort = 3348;//3348 4443
		
		SSLContext sslContext = SSLContext.getInstance("TLSv1"); // SSLv3 TLSv1
		KeyManager[] km = createKeyManagers("jks", keystore);
		TrustManager[] tm = createTrustManagers("jks", keystore);
		SecureRandom random = null;// SecureRandom.getInstance("SHA1PRNG");
		sslContext.init(km, tm, random);

		System.setProperty("javax.net.debug", "ssl");
		System.setProperty("https.protocols", "TLSv1");
		
//		Provider p[] = Security.getProviders();
//		for (int i = 0; i < p.length; i++) {
//			PConsole.print(p[i]);
//			for (Enumeration e = p[i].keys(); e.hasMoreElements();)
//				PConsole.print("\t" + e.nextElement());
//		}

//		java.security.Provider provider = new com.sun.net.ssl.internal.ssl.Provider();
//		Security.addProvider(provider);

		PConsole.print("SSLC Provider: " + sslContext.getProvider());
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		try {
			PConsole.print("It will test to connect "+ucgwIp+":"+sslPort);
			SSLSocket sslSocket = (SSLSocket) ssf.createSocket(ucgwIp, sslPort);
			
//			String[] ps = sslSocket.getSupportedProtocols();
//			for (String protocol : ps) {
//				PConsole.print(protocol);
//			}

//			String[] cipers = sslSocket.getSupportedCipherSuites();
//			for (String ciper : cipers) {
//				PConsole.print(ciper);
//			}

//			PConsole.print(sslSocket.isConnected());

			sslSocket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
				public void handshakeCompleted(HandshakeCompletedEvent event) {
					try {
						PConsole.print("Handshake completed listener invoked.");
						PConsole.print("LocalPrincipal : " + event.getLocalPrincipal());
						PConsole.print("PeerPrincipal  : " + event.getPeerPrincipal());
						Certificate[] certs = event.getPeerCertificates();
						for (int i = 0; certs != null && i < certs.length; i++) {
							Certificate cert = certs[i];
							PConsole.print("PublicKey: " + cert.getPublicKey());
							PConsole.print("Type: " + cert.getType());
						}
					} catch (Exception e) {
						System.err.println(e);
					}
				}
			});

			sslSocket.startHandshake();
			PConsole.print("ssl ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static KeyManager[] createKeyManagers(String keyStoreType, String keyStore) throws Exception {
		KeyStore ks = KeyStore.getInstance(keyStoreType);
		ks.load(new FileInputStream(keyStore), KEY_PASS);
		//String algo = KeyManagerFactory.getDefaultAlgorithm();
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509"/* algo */);
		kmf.init(ks, KEY_PASS);
		return kmf.getKeyManagers();
	}

	public static TrustManager[] createTrustManagers(String keyStoreType, String keyStore) throws Exception {
		KeyStore ks = KeyStore.getInstance(keyStoreType);
		ks.load(new FileInputStream(keyStore), KEY_PASS);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);
		return tmf.getTrustManagers();
	}

}