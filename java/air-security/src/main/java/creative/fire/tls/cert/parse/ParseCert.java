package creative.fire.tls.cert.parse;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import creative.fire.tls.tools.PConsole;

public class ParseCert {

	public static void main(String args[]) throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream("store/test.cer");
		Certificate c = cf.generateCertificate(in);
		in.close();
		PConsole.print("----");
		PConsole.print("证书内容 ");
		PConsole.print(c.toString());
		PConsole.print("----");

		X509Certificate t = (X509Certificate) c;
		byte[] content = t.getEncoded();
		PConsole.print("证书内容 ");
		PConsole.print(exportBase64(content));
		PConsole.print("----");
		
		PConsole.print("版本号 " + t.getVersion());
		PConsole.print("序列号 " + t.getSerialNumber().toString(16));
		PConsole.print("有效期起始日 " + t.getNotBefore());
		PConsole.print("有效期截至日 " + t.getNotAfter());
		PConsole.print("签名算法 " + t.getSigAlgName());

		PConsole.print("被签者全名 " + t.getSubjectDN());
		String issuedTo = t.getSubjectDN().getName();
		PConsole.print("被签者 " + issuedTo);

		PConsole.print("签发者全名\n" + t.getIssuerDN());
		String issuedBy = t.getIssuerDN().getName();
		PConsole.print("签发者 " + issuedBy);

		byte[] sig = t.getSignature();
		PConsole.print("签名\n" + new BigInteger(sig).toString(16));
		PublicKey pk = t.getPublicKey();
		byte[] pkenc = pk.getEncoded();
		PConsole.print("公钥");
		for (int i = 0; i < pkenc.length; i++) {
			System.out.print(pkenc[i] + ",");
		}
	}

	private static String exportBase64(byte[] encodedString) {
		String SEPARATOR = System.getProperty("line.separator");
		String b64 = new sun.misc.BASE64Encoder().encode(encodedString);
		StringBuilder sb = new StringBuilder();
		sb.append("-----BEGIN CERTIFICATE-----").append(SEPARATOR);
		sb.append(b64).append(SEPARATOR);
		sb.append("-----END CERTIFICATE-----").append(SEPARATOR);
		return sb.toString();
	}
}