package creative.fire.tls.cert.main;

import static creative.fire.tls.TLSParameter.*;
import creative.fire.tls.cert.core.CertHandle;

public class MSecond {
	public static void main(String[] args) {
		final String iviewKS = temp + "iview1.ks";
		final String ucgwKS = temp + "ucgw1.ks";

		CertHandle.listKeystore(iviewKS);
		CertHandle.deleteCert(INTER_ALIAS, iviewKS);
		CertHandle.listKeystore(iviewKS);

		CertHandle.listKeystore(ucgwKS);
		CertHandle.deleteCert(INTER_ALIAS, ucgwKS);
		CertHandle.listKeystore(ucgwKS);
	}
}
