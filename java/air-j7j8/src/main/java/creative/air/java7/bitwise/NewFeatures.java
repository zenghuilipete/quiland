package creative.air.java7.bitwise;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author feuyeux@gmail.com 2011-10-5
 */
public class NewFeatures {
	public static void main(String[] args) {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "1");
		System.out.println(map.get(1));

		NewFeatures myTest = new NewFeatures();

		myTest.stringSwitch("Oracle");
	}

	public void multiCatch() {
		try {
			this.getClass().getDeclaredMethod("multiCatch");
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void autoClose() throws FileNotFoundException, IOException {
		try (FileReader reader = new FileReader("file")) {
			int ch;
			while ((ch = reader.read()) != -1) {
				System.out.println(ch);
				reader.read();
			}
		}
	}

	public void stringSwitch(String key) {
		switch (key) {
		case "RedHat":
			System.out.println("jboss");
			break;
		case "Oracle":
			System.out.println("weblogic");
			break;
		case "IBM":
			System.out.println("websphere");
			break;
		default:
			System.out.println("tomcat");
			break;
		}
	}
}
