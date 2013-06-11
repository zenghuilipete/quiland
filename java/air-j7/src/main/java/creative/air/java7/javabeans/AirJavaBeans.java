package creative.air.java7.javabeans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AirJavaBeans {
	private String name;

	public AirJavaBeans() {

	}

	public AirJavaBeans(String name) {
		this.name = name;
	}

	public void introspect() throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(AirJavaBeans.class, Introspector.IGNORE_ALL_BEANINFO);
		MethodDescriptor[] methods = beanInfo.getMethodDescriptors();
		for (MethodDescriptor methodDescriptor : methods) {
			System.out.println(methodDescriptor.getName());
		}
	}

	public void xmlEncode() throws IOException {
		OutputStream output = Files.newOutputStream(Paths.get("me.xml"), StandardOpenOption.CREATE);
		try (XMLEncoder encoder = new XMLEncoder(output, StandardCharsets.UTF_8.name(), true, 0)) {
			AirJavaBeans me = new AirJavaBeans("test");
			encoder.writeObject(me);
		}
	}

	public void xmlDecode() throws IOException {
		InputStream input = Files.newInputStream(Paths.get("me.xml"), StandardOpenOption.READ);
		try (XMLDecoder decoder = new XMLDecoder(input)) {
			AirJavaBeans me = (AirJavaBeans) decoder.readObject();
			System.out.println(me.getName());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
