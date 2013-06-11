package creative.fire.concurrent.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteTest {
	public static void main(String[] args) {
		if (args.length == 0) {
			args = new String[] { " C", " A" };
		}
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
		list.add("1");
		// Replaces the element at the specified position in this list with the specified
		// element.
		String oldValue = list.set(0, "2");
		System.out.println(oldValue);
		printAll(list.iterator());

		List<String> list1 = new CopyOnWriteArrayList<String>(Arrays.asList(args));
		List<String> list2 = new ArrayList<String>(Arrays.asList(args));
		Iterator<String> itor1 = list1.iterator();
		Iterator<String> itor2 = list2.iterator();
		list1.add("New");
		try {
			printAll(itor1);
		} catch (ConcurrentModificationException e) {
			System.err.println("Shouldn't get here");
		}
		list2.add("New");
		try {
			printAll(itor2);
		} catch (ConcurrentModificationException e) {
			System.err.println("Will get here.");
		}
	}

	private static void printAll(Iterator<String> itor) {
		while (itor.hasNext()) {
			System.out.println(itor.next());
		}
	}
}