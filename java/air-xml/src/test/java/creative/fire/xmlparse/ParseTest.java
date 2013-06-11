package creative.fire.xmlparse;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import creative.fire.xmlparse.dom.DOMParsing;
import creative.fire.xmlparse.first.Book;
import creative.fire.xmlparse.first.DOMParseBook;
import creative.fire.xmlparse.first.SAXParseBook;
import creative.fire.xmlparse.jibx.MCU_XML_API;
import creative.fire.xmlparse.sax.SAXParsing;

public class ParseTest extends TestCase {
	private String root = System.getProperty("user.dir");

	public void testSAX() throws Exception {
		SAXParseBook sax = new SAXParseBook();
		InputStream input = new FileInputStream(root + "\\xml\\test.xml");
		List<Book> books = sax.parse(input);
		for (Book book : books) {
			System.out.println(book.toString());
		}
		System.out.println();
	}

	public void testDOM() throws Exception {
		InputStream input = new FileInputStream(root + "\\xml\\test.xml");
		DOMParseBook dom = new DOMParseBook();
		List<Book> books = dom.parse(input);
		for (Book book : books) {
			System.out.println(book.toString());
		}
		System.out.println();
	}

	public void testDOM1() throws Exception {
		InputStream input = new FileInputStream(root + "\\xml\\recordStatus.xml");
		DOMParsing dom = new DOMParsing();
		String status = dom.parse0(input);
		System.out.println("----DOM Testing----");
		System.out.println("parse0:" + status);

		input = new FileInputStream(root + "\\xml\\recordStatus.xml");
		status = dom.parse1(input);
		System.out.println("parse1:" + status);
		System.out.println();
	}

	public void testSAX1() throws Exception {
		InputStream input = new FileInputStream(root + "\\xml\\recordStatus.xml");
		SAXParsing sax = new SAXParsing();
		String status = sax.parse(input);
		System.out.println("----SAX Testing----");
		System.out.println(status);
		System.out.println();
	}

	public void testJIBX() {
		try {
			IBindingFactory bfact = BindingDirectory.getFactory(MCU_XML_API.class);
			IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
			MCU_XML_API mcuXmlAPI = (MCU_XML_API) uctx.unmarshalDocument(new FileInputStream(root + "\\xml\\recordStatus.xml"), null);
			System.out.println("----JIBX Testing----");
			System.out.println("version:" + mcuXmlAPI.getVersion());
			String status = mcuXmlAPI.getRequest().getSet_Recording_Status_Request().getRecordingStatus().getStatus();
			System.out.println("status:" + status);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}