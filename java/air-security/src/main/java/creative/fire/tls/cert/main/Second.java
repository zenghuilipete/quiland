package creative.fire.tls.cert.main;

import static creative.fire.tls.TLSParameter.*;
import creative.fire.tls.TLSParameter;
import creative.fire.tls.cert.CertInfo;
import creative.fire.tls.cert.core.CASign;
import creative.fire.tls.cert.core.CAVarify;
import creative.fire.tls.cert.core.CertHandle;
import creative.fire.tls.cert.core.SelfSign;
import creative.fire.tls.tools.Tools;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class Second {
	public static void main(String[] args) throws Exception {
		// 创建ICM自签证书
		CertInfo certInfo = new CertInfo();
		certInfo.setKeystore(ICM_KEYSTORE);
		certInfo.setAlias(ICM_ALIAS);
		certInfo.setCommonName("mars_icm");
		SelfSign.sign(certInfo, ICM_CER);

		// 创建UCGW自签证书
		//		CertInfo certInfo = new CertInfo();
		//		SelfSign selfs = new SelfSign();
		certInfo.setKeystore(UCGW_KEYSTORE);
		certInfo.setAlias(UCGW_ALIAS);
		certInfo.setCommonName("mars_UCGW");
		SelfSign.sign(certInfo, UCGW_CER);

		// 创建CA证书
		//		SelfSign selfs = new SelfSign();
		//		CertInfo certInfo = new CertInfo();
		certInfo.setKeystore(CA_KEYSTORE);
		certInfo.setAlias(CA_ALIAS);
		certInfo.setCommonName("mars_ca");
		SelfSign.sign(certInfo, CA_CER);

		listKeystore();

		// send icm cer to ca then
		CASign cas = new CASign(CA_KEYSTORE, CA_ALIAS, CA_SIGNED_ALIAS);
		cas.sign(ICM_CER, ICM_SIGN_CER, CASIGN_KEYSTORE);

		// send ucgw cer to ca then
		cas.sign(UCGW_CER, UCGW_SIGN_CER, CASIGN_KEYSTORE);

		CAVarify ck = new CAVarify(TLSParameter.CA_CER);
		// 验证ICM证书
		ck.valid(ICM_SIGN_CER);
		// 验证UCGW证书
		ck.valid(UCGW_SIGN_CER);

		CertHandle.importCert(CA_ALIAS, CA_CER, ICM_KEYSTORE);
		CertHandle.trustCACert(CA_SIGNED_ALIAS, ICM_SIGN_CER, ICM_KEYSTORE);
		CertHandle.trustCACert("opposite", UCGW_SIGN_CER, ICM_KEYSTORE);

		CertHandle.importCert(CA_ALIAS, CA_CER, UCGW_KEYSTORE);
		CertHandle.trustCACert(CA_SIGNED_ALIAS, UCGW_SIGN_CER, UCGW_KEYSTORE);
		CertHandle.trustCACert("opposite", ICM_SIGN_CER, UCGW_KEYSTORE);

		listKeystore();
	}

	public static void listKeystore() throws InterruptedException {
		String cmd = "keytool -list -keystore " + CA_KEYSTORE + " -storepass " + S_STORE_PASS;
		boolean exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
		cmd = "keytool -list -keystore " + ICM_KEYSTORE + " -storepass " + S_STORE_PASS;
		exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
		cmd = "keytool -list -keystore " + UCGW_KEYSTORE + " -storepass " + S_STORE_PASS;
		exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
	}
}
