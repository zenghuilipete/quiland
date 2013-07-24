package creative.fire.pattern.crazyif;

public class Device {
	private String ip;
	private String deviceType;
	private String protocol;

	public Device(String ip, String deviceType) {
		this.ip = ip;
		this.deviceType = deviceType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String call(String saying) {
		return protocol + "::" + ip + "::" + saying;
	}
}
