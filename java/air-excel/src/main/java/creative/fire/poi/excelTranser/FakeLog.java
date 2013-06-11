package creative.fire.poi.excelTranser;

public class FakeLog {
	public static void debug(String message) {
		System.out.println(message);
	}

	public static void debug(String fullPath, String fileContent) {
		// TODO
		System.out.print("[" + fullPath + "]" + SysConf.ENTER + fileContent + SysConf.ENTER);
	}

	public static void info(String message) {
		System.out.println("info: " + message);
	}

	public static void error(String message) {
		System.out.println("error: " + message);
	}
}
