package creative.fire.tls.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import creative.fire.tls.TLSParameter;

/**
 * @author feuyeux@gmail.com 2011-5-23
 */
public class TLSSocket {
	public static KeyManager[] createKeyManagers(String keyStoreType, String keyStore) throws Exception {
		KeyStore ks = KeyStore.getInstance(keyStoreType);
		ks.load(new FileInputStream(keyStore), TLSParameter.KEY_PASS);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, TLSParameter.KEY_PASS);
		return kmf.getKeyManagers();
	}

	public static TrustManager[] createTrustManagers(String keyStoreType, String keyStore) throws Exception {
		KeyStore ks = KeyStore.getInstance(keyStoreType);
		ks.load(new FileInputStream(keyStore), TLSParameter.KEY_PASS);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);
		return tmf.getTrustManagers();
	}
}
