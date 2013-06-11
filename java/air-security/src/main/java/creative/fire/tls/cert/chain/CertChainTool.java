package creative.fire.tls.cert.chain;

import java.io.File;
import java.util.ArrayList;

import creative.fire.tls.cert.CertificateIssueInfo;
import creative.fire.tls.cert.CertificateStatus;
import creative.fire.tls.cert.parse.CertificateParser;

public class CertChainTool {
	private final String jboss = "D:\\views\\luh_iVIEW_7.7_3_int\\iVIEW\\platform\\jboss-4.0.4.GA";
	private final String caroot = jboss + "\\server\\default\\conf\\ca\\caroot.crt";
	private final String path = caroot.substring(0, caroot.lastIndexOf("\\") + 1);
	private final String defaultCert = path + "default.crt";
	private final String INTERCERT_PREFIX = "intermediate";

	public CertificateStatus checkCertificateChain() {
		CertificateIssueInfo defaultCertificate = CertificateParser.getCertificate(defaultCert);
		if (defaultCertificate == null)
			return CertificateStatus.CertificateNotPresent;
		if (defaultCertificate.isExpired())
			return CertificateStatus.CertificateExpired;
		if (defaultCertificate.isNotYetValid())
			return CertificateStatus.CertificateNotYetValid;

		CertificateIssueInfo rootCertificate = CertificateParser.getCertificate(caroot);
		if (rootCertificate == null)
			return CertificateStatus.CACertificateNotPresent;
		if (rootCertificate.isExpired())
			return CertificateStatus.CACertificateExpired;
		if (rootCertificate.isNotYetValid())
			return CertificateStatus.CACertificateNotYetValid;

		if (defaultCertificate.getIssueBy().equals(rootCertificate.getIssueTo()))
			return CertificateStatus.OK;

		ArrayList<CertificateIssueInfo> interCerts = getIntermediateCertificates();

		if (interCerts == null || interCerts.size() == 0)
			return CertificateStatus.InterCertificateNotPresent;
		if (interCerts.get(0).getIssueBy().equals(rootCertificate.getIssueTo())) {
			if (interCerts.get(interCerts.size() - 1).getIssueTo().equals(defaultCertificate.getIssueBy())) {
				for (CertificateIssueInfo interCert : interCerts) {
					if (interCert.isExpired())
						return CertificateStatus.InterCertificateExpired;
					if (interCert.isNotYetValid())
						return CertificateStatus.InterCertificateNotYetValid;
				}
				return CertificateStatus.OK;
			}
		}

		return CertificateStatus.InterCertificateNotPresent;
	}

	private ArrayList<CertificateIssueInfo> getIntermediateCertificates() {
		ArrayList<CertificateIssueInfo> interCerts = new ArrayList<CertificateIssueInfo>();
		File sourceDir = new File(path);
		File[] caFiles = sourceDir.listFiles();
		for (File file : caFiles) {
			if (file.isDirectory())
				continue;
			String filename = file.getName();
			if (filename.startsWith(INTERCERT_PREFIX)) {
				CertificateIssueInfo interCert = CertificateParser.getCertificate(file.getAbsolutePath());
				if (interCert != null)
					interCerts.add(interCert);
			}
		}

		for (int i = 0; i < interCerts.size(); i++) {
			CertificateIssueInfo parent = interCerts.get(i);
			for (int j = i + 1; j < interCerts.size(); j++) {
				CertificateIssueInfo son = interCerts.get(j);
				if (parent.getIssueTo().equals(son.getIssueBy())) {
					if (j != i + 1) {
						CertificateIssueInfo other = interCerts.get(i + 1);
						interCerts.set(i + 1, son);
						interCerts.set(j, other);
					}
					continue;
				}
				// if there is no matching issue-by, that means the chain is broken. the series of the certificate is not a chain.
				if (j == interCerts.size() - 1) {
					return null;
				}
			}
		}
		return interCerts;
	}

	public ArrayList<CertificateIssueInfo> buildCertificateChain(ArrayList<String> files) {
		String[] fs = new String[files.size()];
		files.toArray(fs);
		return buildCertificateChain(fs);
	}

	public ArrayList<CertificateIssueInfo> buildCertificateChain(String[] files) {
		boolean hasRoot = false;
		ArrayList<CertificateIssueInfo> certs = new ArrayList<CertificateIssueInfo>();
		for (int i = 0; i < files.length; i++) {
			CertificateIssueInfo info = null;
			try {
				info = CertificateParser.getCertificate(files[i]);
			} catch (Exception e) {
				return null;
			}

			info.setIndex(i);

			if (info.getIssueBy().equals(info.getIssueTo())) {
				if (!hasRoot) {
					hasRoot = true;
					certs.add(0, info);
				} else {
					return null;
				}
			} else
				certs.add(info);
		}

		for (int i = 0; i < certs.size(); i++) {
			CertificateIssueInfo parent = certs.get(i);
			for (int j = i + 1; j < certs.size(); j++) {
				CertificateIssueInfo son = certs.get(j);
				if (parent.getIssueTo().equals(son.getIssueBy())) {
					if (j != i + 1) {
						CertificateIssueInfo other = certs.get(i + 1);
						certs.set(i + 1, son);
						certs.set(j, other);
					}
					continue;
				}
				// if there is no matching issue-by, that means the chain is broken. the series of the certificate is not a chain.
				if (j == certs.size() - 1) {
					return null;
				}
			}
		}
		return certs;
	}
}
