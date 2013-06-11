package creative.air.xml.sax;

import java.io.File;

import org.junit.Test;

public class TestSax {
	AirSAXParser test=new AirSAXParser();
	@Test
	public void testProcess() {
		File xmlFile = new File(this.getClass().getResource("/").getFile()+"/sketch1.xml");
		File schemaFile =   null;
		test.process(xmlFile, schemaFile);
	}
}