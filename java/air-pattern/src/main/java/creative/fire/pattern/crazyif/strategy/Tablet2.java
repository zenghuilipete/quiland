package creative.fire.pattern.crazyif.strategy;

public class Tablet2 extends Device {

	public Tablet2(String ip) {
		super(ip);
	}

	@Override
	public String call(String saying) {
		return "Android-C-Linux--" + ip + "--" + saying;
	}
}
