package creative.fire.pattern.callback3.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeCallback implements ICallback {
	private final List<ICallback> parts;

	public CompositeCallback() {
		parts = new ArrayList<ICallback>();
	}

	public void register(ICallback part) {
		parts.add(part);
	}

	@Override
	public void invoke(String info) {
		for (ICallback part : parts) {
			part.invoke(info);
		}
	}
}
