package creative.fire.tls.cert;

import static creative.fire.tls.TLSParameter.RSA;
import static creative.fire.tls.TLSParameter.S_KEY_PASS;
import static creative.fire.tls.TLSParameter.S_STORE_PASS;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class CertInfo {
	private String commonName;
	private String organizationUnit;
	private String organization;
	private String locality;
	private String state;
	private String contry;
	private String algorithm;
	private String keypass;
	private String storepass;
	private String alias;
	private String keystore;
	private String csrFile;

	public CertInfo() {
		this.setAlgorithm(RSA);
		this.setOrganization("rcd");
		this.setOrganizationUnit("rv");
		this.setLocality("dongcheng");
		this.setState("bj");
		this.setContry("China");
		this.setKeypass(S_KEY_PASS);
		this.setStorepass(S_STORE_PASS);
	}

	public String getKeystore() {
		return keystore;
	}

	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getKeypass() {
		return keypass;
	}

	public void setKeypass(String keypass) {
		this.keypass = keypass;
	}

	public String getStorepass() {
		return storepass;
	}

	public void setStorepass(String storepass) {
		this.storepass = storepass;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public String getCsrFile() {
		return csrFile;
	}

	public void setCsrFile(String csrFile) {
		this.csrFile = csrFile;
	}
}
