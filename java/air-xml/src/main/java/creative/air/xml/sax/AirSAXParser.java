package creative.air.xml.sax;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class AirSAXParser {
	public void process(File file, File schemaFile) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = null;
		spf.setNamespaceAware(true);
		try {
			SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
			spf.setSchema(schemaFile == null ? sf.newSchema() : sf.newSchema(schemaFile));
			parser = spf.newSAXParser();
		} catch (SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("\nStarting parsing of " + file + "\n");
		AirSAXHandler handler = new AirSAXHandler();
		try {
			parser.parse(file, handler);
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
	}
}