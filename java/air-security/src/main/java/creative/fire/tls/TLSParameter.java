package creative.fire.tls;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class TLSParameter {
	public static final String temp = "certs/";
	public static final int SLEEPTIME = 1000;
	public static boolean quiet = false;
	
	public static final String CA_CER = temp + "root.cer";
	public static final String ICM_CER = temp + "icm_self.cer";
	public static final String UCGW_CER = temp + "ucgw_self.cer";
	public static final String INTER_CER = temp + "inter_self.cer";

	public static final String CA_CSR = temp + "root.csr";
	public static final String ICM_CSR = temp + "icm_self.csr";
	public static final String UCGW_CSR = temp + "ucgw_self.csr";
	public static final String INTER_CSR = temp + "inter_self.csr";

	public static final String ICM_SIGN_CER = temp + "icm_signed.cer";
	public static final String UCGW_SIGN_CER = temp + "ucgw_signed.cer";
	public static final String INTER_SIGN_CER = temp + "inter.cer";
	public static final String INTER_SIGN_CER1 = temp + "inter1.cer";
	public static final String INTER_SIGN_CER2 = temp + "inter2.cer";

	public static final String S_STORE_PASS = "123789";
	public static final String S_KEY_PASS = "123789";

	public static final char[] STORE_PASS = S_STORE_PASS.toCharArray();
	public static final char[] KEY_PASS = S_KEY_PASS.toCharArray();

	public static final String CA_KEYSTORE = temp + "ca.ks";
	public static final String ICM_KEYSTORE = temp + "iview.ks";
	public static final String UCGW_KEYSTORE = temp + "ucgw.ks";
	public static final String CASIGN_KEYSTORE = temp + "ca_sign.ks";
	public static final String INTER_KEYSTORE = temp + "inter.ks";

	public static final String CA_ALIAS = "root";
	public static final String INTER_ALIAS = "intermediate";
	public static final String ICM_ALIAS = "icm";
	public static final String UCGW_ALIAS = "ucgw";
	public static final String CA_SIGNED_ALIAS = "ca_signed";
	public static final String INTER_SIGNED_ALIAS = "inter_signed";

	public static final String MD5_RSA = "MD5WithRSA";
	public static final String RSA = "RSA";
	public static final String SHA1PRNG = "SHA1PRNG";

	public static final String X509 = "X.509";
	public static final String SunX509 = "SunX509";

	public static final String keyStoreType = "jks";
	
	public static final int SSLPORT = 9527;
	public static final String PROTOCOL = "TLSv1"; // SSLv3
}
