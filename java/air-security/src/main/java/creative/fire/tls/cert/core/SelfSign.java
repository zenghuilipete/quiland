package creative.fire.tls.cert.core;

import creative.fire.tls.cert.CertInfo;
import creative.fire.tls.tools.Tools;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class SelfSign {
	public static void sign(CertInfo certInfo, String selfCert) throws Exception {
		// 创建证书
		StringBuilder building = new StringBuilder();
		building.append("keytool -genkey -dname \"");
		building.append("CN=" + certInfo.getCommonName());
		building.append(", OU=" + certInfo.getOrganizationUnit());
		building.append(", O=" + certInfo.getOrganization());
		building.append(", L=ZB, ST=" + certInfo.getState());
		building.append(", C=" + certInfo.getContry());
		building.append("\" -alias " + certInfo.getAlias());
		building.append(" -keyalg " + certInfo.getAlgorithm());
		building.append(" -keystore " + certInfo.getKeystore());
		building.append(" -keypass " + certInfo.getKeypass());
		building.append(" -storepass " + certInfo.getStorepass());
		building.append(" -validity 60");

		boolean exc = Tools.executeCmd(building.toString());
		if (!exc)
			return;
		generateCSR(certInfo, selfCert);

		// 导出证书
		building = new StringBuilder();
		building.append("keytool -export -alias ");
		building.append(certInfo.getAlias());
		building.append(" -keystore ");
		building.append(certInfo.getKeystore());
		building.append(" -storepass ");
		building.append(certInfo.getStorepass());
		building.append(" -rfc -file ");
		building.append(selfCert);
		exc = Tools.executeCmd(building.toString());
		if (!exc)
			return;
	}

	public static void generateCSR(CertInfo certInfo, String selfCert) {
		StringBuilder building = new StringBuilder();
		building.append("keytool -certreq");
		building.append(" -alias ").append(certInfo.getAlias());
		building.append(" -keypass ").append(certInfo.getKeypass());
		building.append(" -file ").append(certInfo.getCsrFile());
		building.append(" -keystore ").append(certInfo.getKeystore());
		building.append(" -storepass ").append(certInfo.getStorepass());
		boolean exc = Tools.executeCmd(building.toString());
		if (!exc)
			return;
	}
}
