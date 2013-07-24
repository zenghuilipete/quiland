package creative.fire.pattern.crazyif.strategy;

public class SetTopBox1 extends Device {
	public SetTopBox1(String ip) {
		super(ip);
		this.protocol = "European";
	}

	@Override
	public String call(String saying) {
		return protocol + "::" + ip + "::" + saying;
	}
}
