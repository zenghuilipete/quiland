package creative.fire.pattern.callback1.strategy;

public class DotCallback implements ICallback {

	@Override
	public void invoke(String info) {
		System.out.println("...\t" + info);
	}
}
