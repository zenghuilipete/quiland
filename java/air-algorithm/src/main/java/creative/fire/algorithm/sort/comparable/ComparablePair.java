/**
 * 
 */
package creative.fire.algorithm.sort.comparable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author luh
 * 
 */
public class ComparablePair implements Comparable<ComparablePair> {
	private String id;
	private int value;

	public ComparablePair(String id, int value) {
		this.id = id;
		this.value = value;
	}

	@Override
	public int compareTo(ComparablePair sdgDistance) {
		if (sdgDistance.getValue() > this.value)
			return -1;
		if (sdgDistance.getValue() < this.value)
			return 1;
		return 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static void main(String[] args) {
		ArrayList<ComparablePair> compas = new ArrayList<ComparablePair>();
		compas.add(new ComparablePair("2", 126));
		compas.add(new ComparablePair("5", 123));
		compas.add(new ComparablePair("7", 129));
		compas.add(new ComparablePair("9", 23));

		ComparablePair[] acompa = compas.toArray(new ComparablePair[] {});
		Arrays.sort(acompa);

		for (ComparablePair compa : acompa) {
			System.out.println(compa.getId());
		}
	}
}
