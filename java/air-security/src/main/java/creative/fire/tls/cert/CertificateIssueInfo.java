package creative.fire.tls.cert;

import java.security.Principal;

/**
 * @author luh
 * @version iview7.7
 * @since 2011.10.28
 */
public class CertificateIssueInfo {
	private int index = -1;
	private Principal issueTo;
	private Principal issueBy;
	private boolean isExpired;
	private boolean isNotYetValid;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Principal getIssueTo() {
		return issueTo;
	}

	public void setIssueTo(Principal issueTo) {
		this.issueTo = issueTo;
	}

	public Principal getIssueBy() {
		return issueBy;
	}

	public void setIssueBy(Principal issueBy) {
		this.issueBy = issueBy;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public boolean isNotYetValid() {
		return isNotYetValid;
	}

	public void setNotYetValid(boolean isNotYetValid) {
		this.isNotYetValid = isNotYetValid;
	}
	
	@Override
	public String toString() {
		return "[issueTo="+issueTo+"] [issueBy="+issueBy+"] [isExpired"+isExpired+"i] [sNotYetValid="+isNotYetValid+"]";
	}

}
