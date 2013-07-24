package creative.fire.pattern.crazyif.strategy;

public class Gateway105 extends Device {

	public Gateway105(String ip) {
		super(ip);
		this.protocol = "openwrt";
	}

	@Override
	public String call(String saying) {
		return protocol + "::" + ip + "::" + saying;
	}
}
