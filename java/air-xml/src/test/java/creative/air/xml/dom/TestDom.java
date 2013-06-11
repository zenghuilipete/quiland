package creative.air.xml.dom;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class TestDom {
	@Test
	public void testProcess() {
		Path xmlFile = Paths.get("src/test/resources/AddressWithDTD.xml");
		new AirDOMParser().process(xmlFile);
	}
}
