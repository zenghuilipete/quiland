package creative.fire.tls.cert.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import creative.fire.tls.cert.CertificateIssueInfo;
import creative.fire.tls.tools.PConsole;

public class CertificateParser {
	private static final int ICM_CertificateNotPresent = 11;
	private static final int ICM_CertificateExpired = 12;
	private static final int ICM_CertificateNotYetValid = 13;
	private static final int ICM_CACertificateExpired = 14;
	private static final int ICM_CACertificateNotYetValid = 15;
	private static final int GENERAL_ERROR = 16;
	private static final int ICM_CACertificateNotPresent = 17;

	public static int checkICMCertification() {
		int result1 = checkCACertificate();
		if (result1 == 1) {
			return checkICMCertificate();
		} else {
			return result1;
		}
	}

	private static int checkICMCertificate() {
		final char[] KEY_PASS = "radvision".toCharArray();
		final String alias = "default";
		final String rootAlias = "root";
		String keystore = "..\\server\\default\\conf\\ca\\b2bua.keystore";
		File b2buaKeystore = new File(keystore);
		if (keystore != null && !b2buaKeystore.exists()) {
			PConsole.print("xmlovertls- Checking the IVIEW CA-signed certificate: " + keystore + ", it doesn't exist.");

			return ICM_CertificateNotPresent;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(b2buaKeystore);

			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(fis, KEY_PASS);

			X509Certificate x509Cert = (X509Certificate) ks.getCertificate(alias);
			try {
				x509Cert.checkValidity();
			} catch (CertificateExpiredException e) {
				return ICM_CertificateExpired;
			} catch (CertificateNotYetValidException e) {
				return ICM_CertificateNotYetValid;
			}

			x509Cert = (X509Certificate) ks.getCertificate(rootAlias);
			try {
				x509Cert.checkValidity();
			} catch (CertificateExpiredException e) {
				return ICM_CACertificateExpired;
			} catch (CertificateNotYetValidException e) {
				return ICM_CACertificateNotYetValid;
			}
		} catch (Exception e) {
			return GENERAL_ERROR;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
			}
		}
		return 1;
	}

	private static int checkCACertificate() {
		// V:\iVIEW\core\deploy\resource\core.properties
		String caroot = "..\\server\\default\\conf\\ca\\caroot.crt";
		File carootFile = new File(caroot);
		if (caroot != null && !carootFile.exists()) {
			PConsole.print("xmlovertls- Checking the IVIEW's CA certificate: " + caroot + ", it doesn't exist.");
			return ICM_CACertificateNotPresent;
		}
		FileInputStream fis = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			fis = new FileInputStream(carootFile);
			java.security.cert.Certificate c1 = cf.generateCertificate(fis);
			X509Certificate x509Cert = (X509Certificate) c1;
			try {
				x509Cert.checkValidity();
			} catch (CertificateExpiredException e) {
				return ICM_CACertificateExpired;
			} catch (CertificateNotYetValidException e) {
				return ICM_CACertificateNotYetValid;
			}

		} catch (Exception e) {
			return GENERAL_ERROR;
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
		}
		return 1;
	}

	public static CertificateIssueInfo getCertificate(String certFile) {
		CertificateFactory cf;
		FileInputStream in = null;
		try {
			cf = CertificateFactory.getInstance("X.509");
			in = new FileInputStream(certFile);
			Certificate c = cf.generateCertificate(in);
			X509Certificate t = (X509Certificate) c;
			CertificateIssueInfo result = new CertificateIssueInfo();
			result.setIssueTo(t.getSubjectDN());
			result.setIssueBy(t.getIssuerDN());

			try {
				t.checkValidity();
			} catch (CertificateExpiredException e) {
				result.setExpired(true);
			} catch (CertificateNotYetValidException e) {
				result.setNotYetValid(true);
			}
			return result;

		} catch (CertificateException e) {
		} catch (FileNotFoundException e) {
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}
