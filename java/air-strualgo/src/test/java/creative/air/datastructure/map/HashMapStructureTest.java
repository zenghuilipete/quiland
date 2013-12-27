package creative.air.datastructure.map;

import org.junit.Test;

import creative.air.datastructure.map.AirHashMap.AirEntry;
/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 16/09/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class HashMapStructureTest {
	AirHashMap<StringInt, Integer> map = new AirHashMap<StringInt, Integer>(1, 1f);

	@Test
	public void testStructure() {
		for (int i = 0; i < 12; i++) {
			map.put(new StringInt(i), new Integer(i));
			print();
		}
	}

	private void print() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("table_len=" + map.getTable().length);
		buffer.append(" map_size=" + map.size() + "\n");
		for (int i = 0; i < map.getTable().length; i++) {
			AirEntry<StringInt, Integer> e = map.getTable()[i];
			if (e != null) {
				buffer.append("table[" + i + "]=\n");
				buffer.append("e.hash=" + e.hash);
				buffer.append("  e.value=" + e.value + "\n");
				while ((e = e.next) != null) {
					buffer.append("->  e.hash=" + e.hash);
					buffer.append("  e.value=" + e.value + "\n");
				}
			} else {
				buffer.append("table[" + i + "]=null\n");
			}
		}
		System.out.println(buffer.toString());
	}
}