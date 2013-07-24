package creative.fire.pattern.crazyif.strategy;

public class SetTopBox2 extends Device {

	public SetTopBox2(String ip) {
		super(ip);
		this.protocol = "Chinese";
	}

	@Override
	public String call(String saying) {
		return "Android-C-Linux::" + ip + "::" + saying;
	}

}
