package persistence;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

public class XmlFileManager implements IXmlFileManager{

    public void write(Document document, String path) {
// write the XML document to disk
        try {

// create DOMSource for source XML document
            Source xmlSource = new DOMSource(document);

// create StreamResult for transformation result
            Result result = new StreamResult(new FileOutputStream(
                    path));

// create TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();

// create Transformer for transformation
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //Java XML Indent

// transform and deliver content to client
            transformer.transform(xmlSource, result);
        }

// handle exception creating TransformerFactory
        catch (TransformerFactoryConfigurationError factoryError) {
            System.err.println("Error creating" + "TransformerFactory");
            factoryError.printStackTrace();
        } catch (TransformerException transformerError) {
            System.err.println("Error transforming document");
            transformerError.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public Document read(String path) {

        DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
        dBF.setIgnoringComments(true); // Ignore the comments present in the
// XML File when reading the xml
        DocumentBuilder builder = null;
        try {
            builder = dBF.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        InputSource input = new InputSource(path);
        Document doc = null;
        try {
            assert builder != null;
            doc = builder.parse(input);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

	@Override
	public Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document document = builder.newDocument();
        
        return document;
	}

}
