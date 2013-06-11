package creative.air.concurrent.detect;

import java.util.Date;

public class DeviceDomain {
	private String name;
	private String status;
	private Date lastUpdate;

	public DeviceDomain(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return "name=" + name + " status=" + status + "  lastUpdate=" + lastUpdate.toGMTString();
	}
}
