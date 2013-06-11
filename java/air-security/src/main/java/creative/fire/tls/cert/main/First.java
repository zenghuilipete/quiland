package creative.fire.tls.cert.main;

import static creative.fire.tls.TLSParameter.CA_ALIAS;
import static creative.fire.tls.TLSParameter.CA_CER;
import static creative.fire.tls.TLSParameter.CA_KEYSTORE;
import static creative.fire.tls.TLSParameter.CA_SIGNED_ALIAS;
import static creative.fire.tls.TLSParameter.ICM_ALIAS;
import static creative.fire.tls.TLSParameter.ICM_CER;
import static creative.fire.tls.TLSParameter.ICM_KEYSTORE;
import static creative.fire.tls.TLSParameter.ICM_SIGN_CER;
import static creative.fire.tls.TLSParameter.S_STORE_PASS;
import static creative.fire.tls.TLSParameter.UCGW_ALIAS;
import static creative.fire.tls.TLSParameter.UCGW_CER;
import static creative.fire.tls.TLSParameter.UCGW_KEYSTORE;
import static creative.fire.tls.TLSParameter.UCGW_SIGN_CER;
import creative.fire.tls.TLSParameter;
import creative.fire.tls.cert.CertInfo;
import creative.fire.tls.cert.core.CASign;
import creative.fire.tls.cert.core.CAVarify;
import creative.fire.tls.cert.core.SelfSign;
import creative.fire.tls.tools.PConsole;
import creative.fire.tls.tools.Tools;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class First {
	public static void main(String[] args) throws Exception {
		PConsole.print("----build self certificate----");
		// 创建CA证书
		CertInfo certInfo = new CertInfo();
		certInfo.setKeystore(CA_KEYSTORE);
		certInfo.setAlias(CA_ALIAS);
		certInfo.setCommonName("mars_ca");
		SelfSign.sign(certInfo, CA_CER);

		// 创建ICM自签证书
		certInfo.setKeystore(ICM_KEYSTORE);
		certInfo.setAlias(ICM_ALIAS);
		certInfo.setCommonName("mars_icm");
		SelfSign.sign(certInfo, ICM_CER);

		// 创建UCGW自签证书
		certInfo.setKeystore(UCGW_KEYSTORE);
		certInfo.setAlias(UCGW_ALIAS);
		certInfo.setCommonName("mars_ucgw");
		SelfSign.sign(certInfo, UCGW_CER);

		listKeystore();

		PConsole.print("\n----ca sign certificate----");
		// CA签发ICM证书
		CASign cas = new CASign(CA_KEYSTORE, CA_ALIAS, CA_SIGNED_ALIAS);
		cas.sign(ICM_CER, ICM_SIGN_CER, ICM_KEYSTORE);

		// CA签发UCGW证书
		cas.sign(UCGW_CER, UCGW_SIGN_CER, UCGW_KEYSTORE);

		// 验证ICM证书
		CAVarify ck = new CAVarify(TLSParameter.CA_CER);
		ck.valid(ICM_SIGN_CER);

		// 验证UCGW证书
		ck.valid(UCGW_SIGN_CER);

		listKeystore();
	}

	public static void listKeystore() throws InterruptedException {
		String cmd = "keytool -list -keystore " + CA_KEYSTORE + " -storepass " + S_STORE_PASS;
		boolean exc = Tools.executeCmd(cmd);
		if (!exc)
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
	}
}
