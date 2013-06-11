package creative.fire.tls.cert.parse;

import static org.junit.Assert.*;

import org.junit.Test;

import creative.fire.tls.cert.CertificateStatus;
import creative.fire.tls.cert.chain.CertChainTool;

public class CertChainToolTest {
	@Test
	public void checkCertificateChain(){
		CertChainTool tool=new CertChainTool();
		CertificateStatus status = tool.checkCertificateChain();
		assertEquals(CertificateStatus.OK,status);
	}
}
