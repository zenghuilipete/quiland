package creative.fire.tls.cert.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;

import creative.fire.tls.TLSParameter;
import creative.fire.tls.tools.PConsole;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class CAVarify {
	private String caCert;

	public CAVarify(String caCert) {
		super();
		this.caCert = caCert;
	}

	public void valid(String certFile) throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance(TLSParameter.X509);
		FileInputStream in1 = new FileInputStream(caCert);
		java.security.cert.Certificate cacert = cf.generateCertificate(in1);
		in1.close();

		FileInputStream in2 = new FileInputStream(certFile);
		java.security.cert.Certificate signcert = cf.generateCertificate(in2);
		in2.close();

		PublicKey pbk = cacert.getPublicKey();
		boolean pass = false;
		try {
			signcert.verify(pbk);
			pass = true;
		} catch (Exception e) {
			pass = false;
			PConsole.print(e.toString());
		}
		if (pass) {
			PConsole.print("It is signed by the CA");
		} else {
			PConsole.print("Not signed by the CA");
		}
	}

	public void validCSR(String csrFile) throws Exception {
		String lineSeparator = System.getProperty("line.separator");
		CertificateFactory cf = CertificateFactory.getInstance(TLSParameter.X509);
		FileInputStream in1 = new FileInputStream(caCert);
		java.security.cert.Certificate cacert = cf.generateCertificate(in1);
		in1.close();

		PublicKey pbk = cacert.getPublicKey();

		
//		FileInputStream in2 = new FileInputStream(csrFile);
//		java.security.cert.Certificate signcert = cf.generateCertificate(in2);
//		in2.close();
		
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(csrFile));
			String tempLine = null;
			while ((tempLine = reader.readLine()) != null) {
				sb.append(tempLine + lineSeparator);
			}
		} catch (IOException ex) {
			return;
		} finally {
			if (null != reader)
				try {
					reader.close();
				} catch (IOException e) {
					// log.error( e );
				}
		}
		
		String csr = sb.toString();
		
		PConsole.print(csr);
		PConsole.print(pbk.toString());
		
	}

	public static void main(String[] args) throws Exception {		
		CAVarify my = new CAVarify("V:/iVIEW/platform/jboss-4.0.4.GA/server/default/conf/ca/intermediate.crt");
		my.validCSR("V:/iVIEW/platform/jboss-4.0.4.GA/server/default/conf/ca/iViewDefaultCSR.csr");
	}
}