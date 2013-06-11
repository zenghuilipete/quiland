package creative.fire.xmlparse.first;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMParseBook {
	public List<Book> parse(InputStream inputStream) throws Exception {
		List<Book> list = new ArrayList<Book>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		Element element = document.getDocumentElement();

		NodeList bookNodes = element.getElementsByTagName("book");
		for (int i = 0; i < bookNodes.getLength(); i++) {
			Element bookElement = (Element) bookNodes.item(i);
			Book book = new Book();
			book.setId(Integer.parseInt(bookElement.getAttribute("id")));
			NodeList childNodes = bookElement.getChildNodes();
			// System.out.println("*****"+childNodes.getLength());
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if ("name".equals(childNodes.item(j).getNodeName())) {
						book.setName(childNodes.item(j).getFirstChild().getNodeValue());
					} else if ("price".equals(childNodes.item(j).getNodeName())) {
						book.setPrice(Float.parseFloat(childNodes.item(j).getFirstChild().getNodeValue()));
					}
				}
			}// end for j
			list.add(book);
		}// end for i
		return list;
	}
}