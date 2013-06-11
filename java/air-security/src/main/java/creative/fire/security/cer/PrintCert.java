package creative.fire.security.cer;

/**
 * @author feuyeux@gmail.com
 * 2011-1-9
 */
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class PrintCert {
	public static void main(String args[]) throws Exception {
		String pass = "iii999_P";
		String alias = "mykey";
		String name = "./file/mykey.keystore";
		FileInputStream in = new FileInputStream(name);
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in, pass.toCharArray());
		Certificate c = ks.getCertificate(alias);
		in.close();
		System.out.println(c.toString());
	}
}