package creative.fire.pattern.callback3.composite;

public class DotCallback implements ICallback {

	@Override
	public void invoke(String info) {
		System.out.println("...\t" + info);
	}
}
