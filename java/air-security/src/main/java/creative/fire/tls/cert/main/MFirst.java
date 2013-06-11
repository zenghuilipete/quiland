package creative.fire.tls.cert.main;

import static creative.fire.tls.TLSParameter.*;
import static creative.fire.tls.TLSParameter.CA_CER;
import static creative.fire.tls.TLSParameter.CA_KEYSTORE;
import creative.fire.tls.cert.CertInfo;
import creative.fire.tls.cert.core.SelfSign;
import creative.fire.tls.cert.parse.ParseKeystore;

public class MFirst {
	public static void main(String[] args) throws Exception {
		CertInfo certInfo = new CertInfo();
		certInfo.setKeystore(CA_KEYSTORE);
		certInfo.setAlias(CA_ALIAS);
		certInfo.setCommonName("mars_ca");
		SelfSign.sign(certInfo, CA_CER);

		ParseKeystore parser = new ParseKeystore();
		parser.output(CA_KEYSTORE, S_STORE_PASS, CA_ALIAS);
	}
}
