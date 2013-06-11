package creative.fire.tls.cert.parse;

import java.io.FileInputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import static creative.fire.tls.TLSParameter.*;

public class GetCertPathCert {
	public static void main(String args[]) throws Exception {
		if(args.length==0){
			args=new String[]{CA_CER,ICM_SIGN_CER,INTER_SIGN_CER1};
		}
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		List<Certificate> mylist = new ArrayList<Certificate>();
		for (int i = 0; i < args.length; i++) {
			FileInputStream in = new FileInputStream(args[i]);
			Certificate c = cf.generateCertificate(in);
			mylist.add(c);
		}
		CertPath cp = cf.generateCertPath(mylist);
		System.out.println(cp);
	}
}
