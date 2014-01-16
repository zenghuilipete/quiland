package creative.air.java7.objects;

import java.util.Comparator;
import java.util.Objects;

public class AirObjects {

	public void use() {
		System.out.println(Objects.compare("a", "A", new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		}));
	}
}
