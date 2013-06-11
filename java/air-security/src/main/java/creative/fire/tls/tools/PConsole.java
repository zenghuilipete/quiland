package creative.fire.tls.tools;

import java.util.Iterator;
import java.util.Properties;

/**
 * @author feuyeux@gmail.com 2011-11-5
 */
public class PConsole {
	public static void print(String s) {
		System.out.println(s);
	}

	public static void main(String[] args) {
		Properties ps = System.getProperties();
		Iterator it = ps.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
