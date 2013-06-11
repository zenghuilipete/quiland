package creative.air.xml.jaxb;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 * Using JSR-000222 JABX
 * @author
 * Eric Han feuyeux@gmail.com
 * 04/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class JaxbParser {
	private JaxbParser() {
	}

	public static Object unmarshal(InputStream xml, Class<?> clazz) {
		Object obj = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(new Class[] { clazz });
			Unmarshaller u = jc.createUnmarshaller();
			obj = u.unmarshal(xml);
		} catch (JAXBException e) {
			throw new RuntimeException("Can't unmarshal the XML file, error message: " + e.getMessage());
		}
		return obj;
	}

	public static String marshal(Object obj, Class<?> clazz) {
		String result = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(new Class[] { clazz });
			Marshaller m = jc.createMarshaller();
			StringWriter writer = new StringWriter();
			m.marshal(obj, writer);
			result = writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("Can't marshal the XML file, error message: " + e.getMessage());
		}
		return result;
	}
}
