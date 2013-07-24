package creative.fire.pattern.crazyif.strategy;

public abstract class Device implements IDevice {
	protected String ip;
	protected String protocol;

	public Device(String ip) {
		this.ip = ip;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
