package creative.air.datastructure.set;

import java.util.Iterator;
import java.util.TreeSet;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TreeSetTest {
	public static void main(String[] args) {
		TreeSet set = new TreeSet();
		set.add(new Parent(5));
		set.add(new Parent(3));
		set.add(new Child(3));
		set.add(new Parent(4));

		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}

}

@SuppressWarnings("rawtypes")
class Parent implements Comparable {
	protected int value = 0;

	public Parent(int value) {
		this.value = value;
	}

	public int compareTo(Object o) {
		System.out.println("method of parent:" + o);
		Parent o1 = (Parent) o;
		return value > o1.value ? 1 : value < o1.value ? -1 : 0;
	}

	@Override
	public String toString() {
		return "" + value;
	}
}

final class Child extends Parent {

	public Child(int value) {
		super(value);
	}

	public int compareTo(Object o) {
		System.out.println("method of child:" + o);
		Parent o1 = (Parent) o;
		return super.value > o1.value ? 1 : super.value < o1.value ? -1 : 0;
	}
}
