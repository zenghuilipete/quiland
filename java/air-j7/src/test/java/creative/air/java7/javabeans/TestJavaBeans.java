package creative.air.java7.javabeans;

import java.beans.IntrospectionException;
import java.io.IOException;

import org.junit.Test;

public class TestJavaBeans {
	AirJavaBeans jb = new AirJavaBeans("testing");

	@Test
	public void testIntrospect() throws IntrospectionException {
		jb.introspect();
	}

	@Test
	public void testXmlCoding() throws IOException {
		jb.xmlEncode();
		jb.xmlDecode();
	}
}
