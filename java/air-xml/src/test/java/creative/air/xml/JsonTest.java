package creative.air.xml;

import java.io.IOException;
import java.util.HashSet;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import creative.air.xml.jaxb.AbcDto;
import creative.air.xml.jaxb.CbaDto;
/**
 * @author
 * Eric Han feuyeux@gmail.com
 * 04/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class JsonTest {
	private ObjectMapper mapper;

	@Before
	public void tearUp() {
		mapper = new ObjectMapper();
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		// make deserializer use JAXB annotations (only)
		mapper.getDeserializationConfig().withAnnotationIntrospector(introspector);
		// make serializer use JAXB annotations (only)
		mapper.getSerializationConfig().withAnnotationIntrospector(introspector);
	}

	@After
	public void tearDown() {
		mapper.setDeserializationConfig(null);
		mapper.setSerializationConfig(null);
		mapper = null;
	}

	@Test
	public void testJackson() throws IOException {
		AbcDto abc = new AbcDto(1, "name", "value");
		CbaDto o1 = new CbaDto(11, "name", "value");
		CbaDto o2 = new CbaDto(12, "name", "value");
		HashSet<CbaDto> set = new HashSet<CbaDto>();
		set.add(o1);
		set.add(o2);
		abc.setCbas(set);
		mapper.writeValue(System.out, abc);
	}
}