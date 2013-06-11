package creative.fire.tls.cert.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.util.Date;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateValidity;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;
import creative.fire.tls.tools.Tools;
import static creative.fire.tls.TLSParameter.*;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class CASign {
	private String caKeyStore;
	private String caAlias;
	private String caSignedAlias;
	
	public CASign(String caKeyStore, String caAlias, String caSignedAlias) {
		this.caKeyStore = caKeyStore;
		this.caAlias = caAlias;
		this.caSignedAlias = caSignedAlias;
	}

	public boolean sign(String cert, String signCert, String keystore) throws Exception {
		// 从密钥库读取CA证书
		FileInputStream in = new FileInputStream(caKeyStore);
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in, STORE_PASS);
		java.security.cert.Certificate c1 = ks.getCertificate(caAlias);

		// 从密钥库读取私钥
		PrivateKey privateKey = (PrivateKey) ks.getKey(caAlias, KEY_PASS);
		in.close();

		// 得到签发者
		byte[] encod1 = c1.getEncoded();
		X509CertImpl cimp1 = new X509CertImpl(encod1);
		X509CertInfo cinfo1 = (X509CertInfo) cimp1.get(X509CertImpl.NAME + "." + X509CertImpl.INFO);
		X500Name issuer = (X500Name) cinfo1.get(X509CertInfo.SUBJECT + "." + CertificateIssuerName.DN_NAME);

		// 获取待签发证书
		CertificateFactory cf = CertificateFactory.getInstance(X509);
		FileInputStream in2 = new FileInputStream(cert);
		java.security.cert.Certificate c2 = cf.generateCertificate(in2);
		in2.close();

		// 从待签发证书提取证书信息
		byte[] encod2 = c2.getEncoded();
		X509CertImpl cimp2 = new X509CertImpl(encod2);
		X509CertInfo cinfo2 = (X509CertInfo) cimp2.get(X509CertImpl.NAME + "." + X509CertImpl.INFO);

		// 设置新证书有效期
		Date begindate = new Date();
		Date enddate = new Date(begindate.getTime() + 3000 * 24 * 60 * 60 * 1000L); // 60 day
		CertificateValidity cv = new CertificateValidity(begindate, enddate);
		cinfo2.set(X509CertInfo.VALIDITY, cv);

		// 设置新证书序列号
		int sn = (int) (begindate.getTime() / 1000);
		CertificateSerialNumber csn = new CertificateSerialNumber(sn);
		cinfo2.set(X509CertInfo.SERIAL_NUMBER, csn);

		// 设置新证书签发者
		cinfo2.set(X509CertInfo.ISSUER + "." + CertificateIssuerName.DN_NAME, issuer);

		// 设置新证书算法
		AlgorithmId algorithm = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
		cinfo2.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algorithm);

		// 创建证书
		X509CertImpl newcert = new X509CertImpl(cinfo2);
		// 私钥签名
		newcert.sign(privateKey, MD5_RSA);

		// 存入密钥库
		ks.setCertificateEntry(caSignedAlias, newcert);
		FileOutputStream out = new FileOutputStream(keystore);
		ks.store(out, STORE_PASS);
		out.close();

		// 导出证书
		String exportcert = "keytool -export -alias " + caSignedAlias + " -keystore " + keystore + " -storepass " + S_STORE_PASS + " -rfc -file " + signCert;
		return Tools.executeCmd(exportcert);
	}
}
