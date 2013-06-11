package creative.fire.tls.cert.main;

import static creative.fire.tls.TLSParameter.*;

import java.io.File;

import creative.fire.tls.TLSParameter;
import creative.fire.tls.cert.CertInfo;
import creative.fire.tls.cert.core.CASign;
import creative.fire.tls.cert.core.CAVarify;
import creative.fire.tls.cert.core.CertHandle;
import creative.fire.tls.cert.core.SelfSign;
import creative.fire.tls.tools.PConsole;
import creative.fire.tls.tools.Tools;

/**
 * @author feuyeux@gmail.com 2011-8-2 certification chain same intermediate CA
 */
public class Forth {
	public static void main(String[] args) throws Exception {
		TLSParameter.quiet = true;
		handleDir(TLSParameter.temp, 2);

		PConsole.print("创建ICM自签证书");
		CertInfo certInfo = new CertInfo();
		certInfo.setKeystore(ICM_KEYSTORE);
		certInfo.setAlias(ICM_ALIAS);
		certInfo.setCommonName("mars_icm");
		certInfo.setCsrFile(ICM_CSR);
		SelfSign.sign(certInfo, ICM_CER);

		PConsole.print("创建UCGW自签证书");
		certInfo.setKeystore(UCGW_KEYSTORE);
		certInfo.setAlias(UCGW_ALIAS);
		certInfo.setCommonName("mars_UCGW");
		certInfo.setCsrFile(UCGW_CSR);
		SelfSign.sign(certInfo, UCGW_CER);

		PConsole.print("创建中间CA自签证书");
		certInfo.setKeystore(INTER_KEYSTORE);
		certInfo.setAlias(INTER_ALIAS);
		certInfo.setCommonName("mars_inter");
		certInfo.setCsrFile(INTER_CSR);
		SelfSign.sign(certInfo, INTER_CER);

		PConsole.print("创建CA证书");
		certInfo.setKeystore(CA_KEYSTORE);
		certInfo.setAlias(CA_ALIAS);
		certInfo.setCommonName("mars_ca");
		certInfo.setCsrFile(CA_CSR);
		SelfSign.sign(certInfo, CA_CER);

		listKeystore();
		// ------
		PConsole.print("CA签发中间CA证书");
		CASign rootCas = new CASign(CA_KEYSTORE, CA_ALIAS, CA_SIGNED_ALIAS);
		rootCas.sign(INTER_CER, INTER_SIGN_CER, CASIGN_KEYSTORE);

		PConsole.print("验证中间CA证书");
		CAVarify ck = new CAVarify(TLSParameter.CA_CER);
		ck.valid(INTER_SIGN_CER);

		PConsole.print("导入中间CA的KeyStore");
		CertHandle.trustCACert(CA_ALIAS, CA_CER, INTER_KEYSTORE);
		CertHandle.importCert(INTER_ALIAS, INTER_SIGN_CER, INTER_KEYSTORE);

		// ------
		PConsole.print("中间CA签发icm证书");
		CASign interCas = new CASign(INTER_KEYSTORE, INTER_ALIAS, INTER_SIGNED_ALIAS);
		interCas.sign(ICM_CER, ICM_SIGN_CER, CASIGN_KEYSTORE);
		PConsole.print("中间CA签发ucgw证书");
		interCas.sign(UCGW_CER, UCGW_SIGN_CER, CASIGN_KEYSTORE);

		ck = new CAVarify(TLSParameter.INTER_SIGN_CER);
		PConsole.print("验证ICM证书");
		ck.valid(ICM_SIGN_CER);
		PConsole.print("验证UCGW证书");
		ck.valid(UCGW_SIGN_CER);

		PConsole.print("导入ICM的KeyStore");
		CertHandle.trustCACert(CA_ALIAS, CA_CER, ICM_KEYSTORE);
		CertHandle.trustCACert(INTER_ALIAS, INTER_SIGN_CER, ICM_KEYSTORE);
		CertHandle.importCert(ICM_ALIAS, ICM_SIGN_CER, ICM_KEYSTORE);

		PConsole.print("导入UCGW的KeyStore");
		CertHandle.trustCACert(CA_ALIAS, CA_CER, UCGW_KEYSTORE);
		CertHandle.trustCACert(INTER_ALIAS, INTER_SIGN_CER, UCGW_KEYSTORE);
		CertHandle.importCert(UCGW_ALIAS, UCGW_SIGN_CER, UCGW_KEYSTORE);

		listKeystore();

		detailKeystore();
		System.out.print("Certificate Generate Successfully!");

		System.exit(0);
	}

	/**
	 * 
	 * @param path
	 * @param action 1 create,2 clear,3 remove
	 */
	public static void handleDir(String path, int action) {
		File dir = new File(path);
		if (dir.exists()) {
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isDirectory()) {
					handleDir(path + "/" + tmp[i].getName(), action);
				} else {
					tmp[i].delete();
				}
			}
			if (action == 3)
				dir.delete();
		} else {
			if (action != 3)
				dir.mkdir();
		}
	}

	public static void detailKeystore() throws InterruptedException {
		if (!TLSParameter.quiet)
			return;
		TLSParameter.quiet = false;

		PConsole.print("---------------------------------------------------------------");
		String cmd = "keytool -list -keystore " + CA_KEYSTORE + " -storepass " + S_STORE_PASS;
		boolean exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
		Thread.sleep(1000);
		cmd = "keytool -list -keystore " + INTER_KEYSTORE + " -storepass " + S_STORE_PASS;
		exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
		Thread.sleep(1000);
		cmd = "keytool -list -v -keystore " + ICM_KEYSTORE + " -storepass " + S_STORE_PASS;
		exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
		Thread.sleep(1000);
		cmd = "keytool -list -v -keystore " + UCGW_KEYSTORE + " -storepass " + S_STORE_PASS;
		exc = Tools.executeCmd(cmd);
		if (!exc)
			return;
		PConsole.print("---------------------------------------------------------------");
	}

	public static void listKeystore() throws InterruptedException {
		if (TLSParameter.quiet)
			return;
		PConsole.print("---------------------------------------------------------------");
		CertHandle.listKeystore(CA_KEYSTORE);
		CertHandle.listKeystore(INTER_KEYSTORE);
		CertHandle.listKeystore(ICM_KEYSTORE);
		CertHandle.listKeystore(UCGW_KEYSTORE);
		PConsole.print("---------------------------------------------------------------");
	}
}
