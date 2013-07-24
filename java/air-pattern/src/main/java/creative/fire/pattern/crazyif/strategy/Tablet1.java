package creative.fire.pattern.crazyif.strategy;

public class Tablet1 extends Device {

	public Tablet1(String ip) {
		super(ip);
	}

	@Override
	public String call(String saying) {
		return "Android-Linux" + "--" + ip + "--" + saying;
	}
}
