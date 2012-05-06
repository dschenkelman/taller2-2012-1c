package persistence;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public interface IXmlFileManager {
	void write(Document document, String filePath);
	Document read(String filePath);
	Document createDocument() throws ParserConfigurationException;
}
