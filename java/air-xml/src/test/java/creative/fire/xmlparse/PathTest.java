package creative.fire.xmlparse;

import java.io.File;

public class PathTest {
	public static void main(String[] args) {
		Thread.currentThread().getContextClassLoader().getResource("");
		System.out.println(PathTest.class.getClassLoader().getResource(""));
		System.out.println(ClassLoader.getSystemResource(""));
		System.out.println(PathTest.class.getResource(""));
		System.out.println(PathTest.class.getResource("/"));
		
		System.out.println(new File("").getAbsolutePath());
		System.out.println(System.getProperty("user.dir"));
	}
//	file:/D:/sourceCodeCenter/rvtests/XMLParsing/target/test-classes/
//	file:/D:/sourceCodeCenter/rvtests/XMLParsing/target/test-classes/
//	file:/D:/sourceCodeCenter/rvtests/XMLParsing/target/test-classes/creative/fire/xmlparse/
//	file:/D:/sourceCodeCenter/rvtests/XMLParsing/target/test-classes/
//	D:\sourceCodeCenter\rvtests\XMLParsing
//	D:\sourceCodeCenter\rvtests\XMLParsing
}
