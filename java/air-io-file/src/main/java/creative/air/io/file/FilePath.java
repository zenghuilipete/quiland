package creative.air.io.file;

public class FilePath {
	public String getClassDir() {
		return this.getClass().getResource("/").getPath();
	}

	public String getUserDir() {
		String relativelyPath = System.getProperty("user.dir");
		return relativelyPath;
	}

	public static void main(String[] args) {
		FilePath test = new FilePath();
		System.out.println(test.getClassDir());
		System.out.println(test.getUserDir());
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
	}
}
