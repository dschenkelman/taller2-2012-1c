package persistence.test;

import junit.framework.Assert;
import models.IdGroupCollection;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import persistence.IdGroupCollectionXmlManager;
import persistence.XmlManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class IdGroupCollectionManagerXmlTest {
    private static String PATH = "IdGroupCollection.xml";

    @Test
    public void testCreateElementFromIdGroupCollection() {
        IdGroupCollection idGroupCollection = new IdGroupCollection();
        for (int i = 0; i < 10; i++) {
            try {
                idGroupCollection.addIdGroup(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder builder = dBF.newDocumentBuilder(); // java xml documentbuilder
            document = builder.newDocument();
        } catch (ParserConfigurationException parserException) {
            parserException.printStackTrace();
        }

        assert document != null;
        Element root = document.createElement("diagram");
        document.appendChild(root);
        Element attribute = document.createElement("attribute");
        root.appendChild(attribute);

        Element idGroups = IdGroupCollectionXmlManager.getIdGroupCollectionToAttribute(idGroupCollection, document);
        attribute.appendChild(idGroups);

        XmlManager.writeToFile(document, PATH);
    }

    @Test
    public void testCreateIdGroupCollectionFromXml() {
        IdGroupCollection idGroupCollection = new IdGroupCollection();
        for (int i = 0; i < 10; i++) {
            try {
                idGroupCollection.addIdGroup(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder builder = dBF.newDocumentBuilder(); // java xml documentbuilder
            document = builder.newDocument();
        } catch (ParserConfigurationException parserException) {
            parserException.printStackTrace();
        }

        assert document != null;
        Element root = document.createElement("diagram");
        document.appendChild(root);
        Element attribute = document.createElement("attribute");
        root.appendChild(attribute);

        Element idGroups = IdGroupCollectionXmlManager.getIdGroupCollectionToAttribute(idGroupCollection, document);
        attribute.appendChild(idGroups);

        XmlManager.writeToFile(document, PATH);



        IdGroupCollection idGroupCollectionFromXml = IdGroupCollectionXmlManager.getIdGroupCollectionFromElement((Element) XmlManager.readXml(PATH).getDocumentElement().getElementsByTagName("attribute").item(0));

        for (int i = 0; i < 10; i++) {
            try {
                Assert.assertEquals(true, idGroupCollectionFromXml.exists(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
