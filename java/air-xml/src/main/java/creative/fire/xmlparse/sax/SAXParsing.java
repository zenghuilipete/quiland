package creative.fire.xmlparse.sax;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParsing extends DefaultHandler {
	private boolean isPrint;
	private String s;

	public String parse(InputStream xmlStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlStream, this);
		return s;
	}

	@Override
	public void startElement(String uri, String localName, String qname, Attributes attr) {
		if (qname.equals("Status"))
			isPrint = true;
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		if (isPrint) {
			s = new String(ch, start, length);
			isPrint = false;
		}
	}
}