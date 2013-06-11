package creative.fire.tls.cert.core;

import static creative.fire.tls.cert.TLSParameter.X509;

import java.io.FileInputStream;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

public class DateVerify {
	public void valid(String certfile) throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance(X509);
		FileInputStream in = new FileInputStream(certfile);
		java.security.cert.Certificate c = cf.generateCertificate(in);
		in.close();
		X509Certificate t = (X509Certificate) c;

		Date d = new Date();

		System.out.println(d);
		try {
			t.checkValidity(d);
			System.out.println("OK");
		} catch (CertificateExpiredException e) { // 过期
			System.out.println("Expired");
			System.out.println(e.getMessage());
		} catch (CertificateNotYetValidException e) { // 尚未生效
			System.out.println("Too early");
			System.out.println(e.getMessage());
		}
	}
}
