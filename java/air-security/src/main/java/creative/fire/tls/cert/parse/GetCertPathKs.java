package creative.fire.tls.cert.parse;

import static creative.fire.tls.TLSParameter.ICM_ALIAS;
import static creative.fire.tls.TLSParameter.ICM_KEYSTORE;
import static creative.fire.tls.TLSParameter.KEY_PASS;
import static creative.fire.tls.TLSParameter.S_STORE_PASS;
import static creative.fire.tls.TLSParameter.X509;
import static creative.fire.tls.TLSParameter.keyStoreType;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import creative.fire.tls.tools.PConsole;

public class GetCertPathKs {

	public static void main(String args[]) throws Exception {
		String storename = ICM_KEYSTORE;
		char[] storepass = S_STORE_PASS.toCharArray();
		String alias = ICM_ALIAS;

		KeyStore ks = KeyStore.getInstance(keyStoreType);
		ks.load(new FileInputStream(storename), storepass);
		PrivateKey privateKey = (PrivateKey) ks.getKey(alias, KEY_PASS);

		java.security.cert.Certificate[] cchain = ks.getCertificateChain(alias);

		List<Certificate> mylist = new ArrayList<Certificate>();
		for (int i = 0; i < cchain.length; i++) {
			mylist.add(cchain[i]);
		}
		CertificateFactory cf = CertificateFactory.getInstance(X509);
		CertPath cp = cf.generateCertPath(mylist);

		List<? extends Certificate> certificates = cp.getCertificates();
		for (Certificate certificate : certificates) {
			X509Certificate x509Certificate = (X509Certificate) certificate;
			PConsole.print(x509Certificate.getSubjectDN().toString());
			PConsole.print("issued by:" + x509Certificate.getIssuerDN());
			PConsole.print(x509Certificate.getPublicKey() + "\n");
		}

		// PConsole.print(privateKey);

		String pri = new BigInteger(privateKey.getEncoded()).toString(16);
		PConsole.print(pri);
	}
}
