package creative.fire.tls.cert.parse;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import creative.fire.tls.tools.PConsole;

public class ParseKeystore {
	public void output(String keystore, String pass, String alias) throws Exception {
		FileInputStream in = new FileInputStream(keystore);
		// 注意,K, S均大写
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in, pass.toCharArray());

		Certificate c = ks.getCertificate(alias);

		if (c == null)
			return;

		X509Certificate t = (X509Certificate) c;
		outputDetails(t);
		in.close();
	}

	private void outputDetails(X509Certificate t) {
		PConsole.print("版本号 " + t.getVersion());
		PConsole.print("序列号 " + t.getSerialNumber().toString(16));
		PConsole.print("有效期起始日 " + t.getNotBefore());
		PConsole.print("有效期截至日 " + t.getNotAfter());
		PConsole.print("签名算法 " + t.getSigAlgName());

		PConsole.print("全名 " + t.getSubjectDN());
		String issuedTo = t.getSubjectDN().getName();
		PConsole.print("issuedTo " + issuedTo);

		PConsole.print("签发者全名\n" + t.getIssuerDN());
		String issuedBy = t.getIssuerDN().getName();
		PConsole.print("issuedBy " + issuedBy);

		byte[] sig = t.getSignature();
		PConsole.print("签名\n" + new BigInteger(sig).toString(16));
		PublicKey pk = t.getPublicKey();
		byte[] pkenc = pk.getEncoded();
		PConsole.print("公钥");
		for (int i = 0; i < pkenc.length; i++) {
			System.out.print(pkenc[i] + ",");
		}
	}

	public static void main(String args[]) throws Exception {
		// PConsole.print(c);
	}
}

/*
 * 若使用默认的keystore则可如下获取路径名称 String userhome=System.getProperty("user.home"); String name=userhome+File.separator+".keystore";
 */
