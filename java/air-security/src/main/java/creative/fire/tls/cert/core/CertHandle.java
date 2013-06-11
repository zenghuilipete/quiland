package creative.fire.tls.cert.core;

import static creative.fire.tls.TLSParameter.S_KEY_PASS;
import static creative.fire.tls.TLSParameter.S_STORE_PASS;
import creative.fire.tls.tools.Tools;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class CertHandle {

	public static boolean importCert(String alias, String cert, String keystore) {
		String cmd = "keytool -importcert -noprompt -alias " + alias + " -file \"" + cert + "\" -keystore " + keystore + " -storepass " + S_STORE_PASS
				+ " -keypass " + S_KEY_PASS;
		return Tools.executeCmd(cmd);
	}

	public static boolean trustCACert(String alias, String cert, String keystore) {
		String cmd = "keytool -importcert -noprompt -trustcacerts -alias " + alias + " -file \"" + cert + "\" -keystore " + keystore + " -storepass "
				+ S_STORE_PASS + " -keypass " + S_KEY_PASS;
		return Tools.executeCmd(cmd);
	}

	public static boolean deleteCert(String alias, String keystore) {
		String cmd = "keytool -delete -alias " + alias + " -keystore " + keystore + " -storepass " + S_STORE_PASS;
		return Tools.executeCmd(cmd);
	}

	public static boolean listKeystore(String keystore) {
		String cmd = "keytool -list -keystore " + keystore + " -storepass " + S_STORE_PASS;
		return Tools.executeCmd(cmd);
	}
}
