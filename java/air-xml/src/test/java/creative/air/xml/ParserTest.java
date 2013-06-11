package creative.air.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;

import org.junit.Test;

import creative.air.xml.jaxb.AbcDto;
import creative.air.xml.jaxb.CbaDto;
import creative.air.xml.jaxb.JaxbParser;

/**
 * @author
 * Eric Han feuyeux@gmail.com
 * 04/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class ParserTest {
	private String XML_FILE = "/abc.xml";

	@Test
	public void testUnmarshal() throws FileNotFoundException {
		InputStream xmlInput = this.getClass().getResourceAsStream(XML_FILE);
		AbcDto abcDto = (AbcDto) JaxbParser.unmarshal(xmlInput, AbcDto.class);
		System.out.println(abcDto);
	}

	@Test
	public void testMarshal() {
		AbcDto abc = new AbcDto(1, "name", "value");
		CbaDto o1 = new CbaDto(11, "name", "value");
		CbaDto o2 = new CbaDto(12, "name", "value");
		HashSet<CbaDto> set = new HashSet<CbaDto>();
		set.add(o1);
		set.add(o2);
		abc.setCbas(set);
		String result = JaxbParser.marshal(abc, AbcDto.class);
		System.out.println(result);
	}
}
