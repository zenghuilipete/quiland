package creative.fire.tls.cert;
/**
 * UCGW Certificate Status
 * 
 * @since 2011.6.16
 * @version iView7.7
 * @author luh
 */
public enum CertificateStatus {
	OK(0), 
	CACertificateNotPresent(6),
    CACertificateExpired(7),
    CACertificateNotYetValid(8),
    
    CertificateNotPresent(9),
    CertificateExpired(10),
    CertificateNotYetValid(11),
	
    InterCertificateNotPresent(12),
    InterCertificateExpired(13),
    InterCertificateNotYetValid(14),
    
    TEMPORARY_ERROR(15),
    
	GENERAL_ERROR(100);	
	
	CertificateStatus(int value) {
		this.value = value;
	}

	private final int value;

	public int getValue() {
		return value;
	}
}