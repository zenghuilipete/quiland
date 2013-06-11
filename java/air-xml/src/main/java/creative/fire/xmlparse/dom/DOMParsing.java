package creative.fire.xmlparse.dom;

import java.io.InputStream;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMParsing {
	public String parse0(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);

		NodeList node0s = document.getElementsByTagName("Request");

		for (int i = 0; i < node0s.getLength(); i++) {
			Node node0 = node0s.item(i);// 0
			NodeList Request = node0.getChildNodes();

			for (int j = 0; j < Request.getLength(); j++) {
				Node node1 = Request.item(j);// 1
				if (node1.hasChildNodes()) {
					NodeList Set_Recording_Status_Request = node1.getChildNodes();
					for (int k = 0; k < Set_Recording_Status_Request.getLength(); k++) {
						Node node2 = Set_Recording_Status_Request.item(k);// 3
						String nodeName2 = node2.getNodeName();

						if ("RecordingStatus".equals(nodeName2)) {
							NodeList RecordingStatus = node2.getChildNodes();

							for (int m = 0; m < RecordingStatus.getLength(); m++) {
								Node node3 = RecordingStatus.item(m);
								String nodeName4 = node3.getNodeName();

								if ("Status".equals(nodeName4)) {
									return node3.getFirstChild().getNodeValue();
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public String parse1(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		NodeList node0s = document.getElementsByTagName("Request");
		Element root = (Element) node0s.item(0);
		String s = getTagValueByFullName(root, "Set_Recording_Status_Request>RecordingStatus>Status");
		return s;
	}

	public static String getTagValueByFullName(Element root, String fullTagName) {
		Element el = getElementByFullTagName(root, fullTagName);
		if (el != null) {
			Node child = el.getFirstChild();
			if (child != null)
				return child.getNodeValue();
		}

		return null;
	}

	public static Element getElementByFullTagName(Element root, String fullTagName) {
		StringTokenizer tokenizer = new StringTokenizer(fullTagName, ">");
		Element element = null;
		while (tokenizer.hasMoreTokens()) {
			String tagName = tokenizer.nextToken();
			NodeList list = root.getChildNodes();
			Element tmpElement = null;
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element && node.getNodeName().equals(tagName))
					tmpElement = (Element) node;
			}

			if (tmpElement == null) // not found.
				break;
			else {
				if (tokenizer.hasMoreTokens())
					root = tmpElement;
				else
					element = tmpElement;
			}
		}

		return element;
	}
}
