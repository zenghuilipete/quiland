package creative.air.xml.dom;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.io.*;
import java.nio.file.*;
import static org.w3c.dom.Node.*; // For node type constants

public class AirDOMParser implements ErrorHandler {
	public void process(Path xmlFile) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true); // Set namespace aware
		builderFactory.setValidating(true); // & validating parser

		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder(); // Create the parser
			builder.setErrorHandler(new AirDOMParser()); //Error handler is TryDOM instance
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}

		Document xmlDoc = null;
		try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(xmlFile))) {
			xmlDoc = builder.parse(in);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		DocumentType doctype = xmlDoc.getDoctype(); // Get the DOCTYPE node
		if (doctype == null) { // If it�s not null...
			System.out.println("DOCTYPE is null");
		} else { // ...output it
			System.out.println("DOCTYPE node:\n" + doctype.getInternalSubset());
		}

		System.out.println("\nDocument body contents are:");
		listNodes(xmlDoc.getDocumentElement(), " "); // Root element & children
	}

	// output a node and all its child nodes
	static void listNodes(Node node, String indent) {
		// List the current node
		String nodeName = node.getNodeName();
		System.out.println(indent + " Node: " + nodeName);
		System.out.println(indent + " Node Type: " + nodeType(node.getNodeType()));

		NodeList list = node.getChildNodes(); // Get the list of child nodes
		if (list.getLength() > 0) { // If there are some...
			//...list them & their children...
			// ...by calling listNodes() for each
			System.out.println(indent + " Child Nodes of " + nodeName + " are:");
			for (int i = 0; i < list.getLength(); ++i) {
				listNodes(list.item(i), indent + "  ");
			}
		}
	}

	// Method to identify the node type
	static String nodeType(short type) {
		switch (type) {
		case ELEMENT_NODE:
			return "Element";
		case DOCUMENT_TYPE_NODE:
			return "Document type";
		case ENTITY_NODE:
			return "Entity";
		case ENTITY_REFERENCE_NODE:
			return "Entity reference";
		case NOTATION_NODE:
			return "Notation";
		case TEXT_NODE:
			return "Text";
		case COMMENT_NODE:
			return "Comment";
		case CDATA_SECTION_NODE:
			return "CDATA Section";
		case ATTRIBUTE_NODE:
			return "Attribute";
		case PROCESSING_INSTRUCTION_NODE:
			return "Attribute";
		}
		return "Unidentified";
	}

	public void fatalError(SAXParseException spe) throws SAXException {
		System.out.println("Fatal error at line " + spe.getLineNumber());
		System.out.println(spe.getMessage());
		throw spe;
	}

	public void warning(SAXParseException spe) {
		System.out.println("Warning at line " + spe.getLineNumber());
		System.out.println(spe.getMessage());
	}

	public void error(SAXParseException spe) {
		System.out.println("Error at line " + spe.getLineNumber());
		System.out.println(spe.getMessage());
	}
}
