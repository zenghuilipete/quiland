package creative.air.java7.hash;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HashO {
	private int id;

	public HashO() {
	}

	public HashO(int id) {
		this.id = id;
	}

//	@Override
//	public boolean equals(Object obj) {
//		HashO other = (HashO) obj;
//		return id == other.id;
//	}
//
//	@Override
//	public int hashCode() {
//		return id;
//	}

	@Override
	public String toString() {
		return "" + id;
	}

	public static void main(String[] args) {
		Map<HashO, String> map = new HashMap<>();
		HashO key1 = new HashO(1);
		HashO key2 = new HashO(1);
		map.put(key1, "test1");
		map.put(key2, "test2");

		Iterator<Entry<HashO, String>> i = map.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<HashO, String> entry = i.next();
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}
