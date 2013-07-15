package creative.fire.pattern.callback3.composite;

public class Execute {

	public void service(ICallback callback, int callTime) {
		for (int i = 0; i < callTime; i++) {
			String info = "processing " + i;
			callback.invoke(info);
		}
	}
}
