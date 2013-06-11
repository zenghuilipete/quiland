package creative.fire.tls.cert.main;

import static creative.fire.tls.TLSParameter.CASIGN_KEYSTORE;
import static creative.fire.tls.TLSParameter.CA_ALIAS;
import static creative.fire.tls.TLSParameter.CA_CER;
import static creative.fire.tls.TLSParameter.CA_KEYSTORE;
import static creative.fire.tls.TLSParameter.CA_SIGNED_ALIAS;
import static creative.fire.tls.TLSParameter.ICM_ALIAS;
import static creative.fire.tls.TLSParameter.ICM_CER;
import static creative.fire.tls.TLSParameter.ICM_KEYSTORE;
import static creative.fire.tls.TLSParameter.ICM_SIGN_CER;
import static creative.fire.tls.TLSParameter.INTER_SIGN_CER1;
import static creative.fire.tls.TLSParameter.INTER_SIGN_CER2;
import static creative.fire.tls.TLSParameter.S_STORE_PASS;
import static creative.fire.tls.TLSParameter.UCGW_ALIAS;
import static creative.fire.tls.TLSParameter.UCGW_CER;
import static creative.fire.tls.TLSParameter.UCGW_KEYSTORE;
import static creative.fire.tls.TLSParameter.UCGW_SIGN_CER;
import creative.fire.tls.cert.CertInfo;
import creative.fire.tls.cert.core.CASign;
import creative.fire.tls.cert.core.CertHandle;
import creative.fire.tls.cert.core.SelfSign;
import creative.fire.tls.tools.PConsole;
import creative.fire.tls.tools.Tools;

/**
 * @author feuyeux@gmail.com 2011-8-2 certification chain different intermedia CA
 */
public class Fifth {
	private static String INTER_KEYSTORE1 = "inter1.keystore";
	private static String INTER_ALIAS1 = "intermediary1";
	private static String INTER_CER1 = "inter1.self.cer";

	private static String INTER_SIGNED_ALIAS1 = "inter1_signed";

	private static String INTER_KEYSTORE2 = "inter2.keystore";
	private static String INTER_ALIAS2 = "intermediary2";
	private static String INTER_CER2 = "inter2.self.cer";

	private static String INTER_SIGNED_ALIAS2 = "inter2_signed";

	public static void main(String[] args) throws Exception {
		// 创建ICM自签证书
		CertInfo certInfo = new CertInfo();
		certInfo.setKeystore(ICM_KEYSTORE);
		certInfo.setAlias(ICM_ALIAS);
		certInfo.setCommonName("mars_icm");
		SelfSign.sign(certInfo, ICM_CER);

		// 创建UCGW自签证书
		certInfo.setKeystore(UCGW_KEYSTORE);
		certInfo.setAlias(UCGW_ALIAS);
		certInfo.setCommonName("mars_UCGW");
		SelfSign.sign(certInfo, UCGW_CER);

		// 创建中间CA1自签证书
		certInfo.setKeystore(INTER_KEYSTORE1);
		certInfo.setAlias(INTER_ALIAS1);
		certInfo.setCommonName("mars_inter1");
		SelfSign.sign(certInfo, INTER_CER1);

		// 创建中间CA2自签证书
		certInfo.setKeystore(INTER_KEYSTORE2);
		certInfo.setAlias(INTER_ALIAS2);
		certInfo.setCommonName("mars_inter2");
		SelfSign.sign(certInfo, INTER_CER2);

		// 创建CA证书
		certInfo.setKeystore(CA_KEYSTORE);
		certInfo.setAlias(CA_ALIAS);
		certInfo.setCommonName("mars_ca");
		SelfSign.sign(certInfo, CA_CER);

		listKeystore(false);

		CASign rootCas = new CASign(CA_KEYSTORE, CA_ALIAS, CA_SIGNED_ALIAS);
		// CA签发中间CA1证书
		rootCas.sign(INTER_CER1, INTER_SIGN_CER1, CASIGN_KEYSTORE);
		// CA签发中间CA2证书
		rootCas.sign(INTER_CER2, INTER_SIGN_CER2, CASIGN_KEYSTORE);

		// 导入中间CA1的KeyStore
		CertHandle.importCert(CA_ALIAS, CA_CER, INTER_KEYSTORE1);
		CertHandle.trustCACert(INTER_ALIAS1, INTER_SIGN_CER1, INTER_KEYSTORE1);

		// 导入中间CA2的KeyStore
		CertHandle.importCert(CA_ALIAS, CA_CER, INTER_KEYSTORE2);
		CertHandle.trustCACert(INTER_ALIAS2, INTER_SIGN_CER2, INTER_KEYSTORE2);

		listKeystore(true);

		// 中间CA1签发icm证书
		CASign interCas = new CASign(INTER_KEYSTORE1, INTER_ALIAS1, INTER_SIGNED_ALIAS1);
		interCas.sign(ICM_CER, ICM_SIGN_CER, CASIGN_KEYSTORE);

		// 中间CA2签发ucgw证书
		interCas = new CASign(INTER_KEYSTORE2, INTER_ALIAS2, INTER_SIGNED_ALIAS2);
		interCas.sign(UCGW_CER, UCGW_SIGN_CER, CASIGN_KEYSTORE);

		// 导入ICM的KeyStore
		CertHandle.importCert(CA_ALIAS, CA_CER, ICM_KEYSTORE);
		CertHandle.importCert(INTER_ALIAS1, INTER_SIGN_CER1, ICM_KEYSTORE);
		CertHandle.importCert(ICM_ALIAS, ICM_SIGN_CER, ICM_KEYSTORE);
		CertHandle.importCert(INTER_ALIAS2, INTER_SIGN_CER2, ICM_KEYSTORE); // (String alias, String cert, String keystore)

		// 导入UCGW的KeyStore
		CertHandle.importCert(CA_ALIAS, CA_CER, UCGW_KEYSTORE);
		CertHandle.importCert(INTER_ALIAS2, INTER_SIGN_CER2, UCGW_KEYSTORE);
		CertHandle.importCert(UCGW_ALIAS, UCGW_SIGN_CER, UCGW_KEYSTORE);
		CertHandle.importCert(INTER_ALIAS1, INTER_SIGN_CER1, UCGW_KEYSTORE);

		listKeystore(false);

		System.out.print("Certificate Generate Successfully!");
	}

	public static void listKeystore(boolean onlyMediated) throws InterruptedException {
		PConsole.print("(---------------------------------------------------------------");
		String cmd = "keytool -list -keystore " + CA_KEYSTORE + " -storepass " + S_STORE_PASS;
		boolean exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
		Thread.sleep(1000);
		cmd = "keytool -list -keystore " + INTER_KEYSTORE1 + " -storepass " + S_STORE_PASS;
		  exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
		cmd = "keytool -list -keystore " + INTER_KEYSTORE2 + " -storepass " + S_STORE_PASS;
		  exc = Tools.executeCmd(cmd);
			if (!exc)
				return;
		if (onlyMediated)
			return;
		Thread.sleep(1000);
		cmd = "keytool -list -keystore " + ICM_KEYSTORE + " -storepass " + S_STORE_PASS;
		  exc = Tools.executeCmd(cmd);
			if (!exc)
				return;
		Thread.sleep(1000);
		cmd = "keytool -list -keystore " + UCGW_KEYSTORE + " -storepass " + S_STORE_PASS;
		  exc = Tools.executeCmd(cmd);
			if (!exc)
				return;
		PConsole.print("---------------------------------------------------------------)");
	}
}
