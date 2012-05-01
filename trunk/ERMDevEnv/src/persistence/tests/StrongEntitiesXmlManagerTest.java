package persistence.tests;

import junit.framework.Assert;
import models.Entity;
import models.IStrongEntity;
import models.StrongEntityCollection;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import persistence.StrongEntitiesXmlManager;
import persistence.XmlManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.UUID;

public class StrongEntitiesXmlManagerTest {
    private static final String STRONG_ENTITIES_TAG = "strongEntities";
    private static final String STRONG_ENTITY_TAG = "strongEntity";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String ID_ATTRIBUTE = "id";
    private static final String PATH = "StrongEntityCollectionXmlManager.xml";

    @Test
    public void testCreateElementFromStrongEntityCollection() {
        Entity entity0 = new Entity("TestEntity0");
        Entity entity1 = new Entity("TestEntity1");
        Entity entity2 = new Entity("TestEntity2");
        Entity entity3 = new Entity("TestEntity3");
        Entity entity4 = new Entity("TestEntity4");

        StrongEntityCollection strongEntityCollection = new StrongEntityCollection();
        try {
            strongEntityCollection.addStrongEntity(entity0);
            strongEntityCollection.addStrongEntity(entity1);
            strongEntityCollection.addStrongEntity(entity2);
            strongEntityCollection.addStrongEntity(entity3);
            strongEntityCollection.addStrongEntity(entity4);
        } catch (Exception e) {
            Assert.fail();
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
        Element diagram = document.createElement("diagram");
        document.appendChild(diagram);

        Element strongEntityCollectionElement = new StrongEntitiesXmlManager().getElementFromItem(strongEntityCollection, document);
        diagram.appendChild(strongEntityCollectionElement);

        XmlManager.writeToFile(document, PATH);

        NodeList nodeList = diagram.getElementsByTagName(STRONG_ENTITIES_TAG);
        Element element = (Element) nodeList.item(0);
        NodeList strongEntitiesElements = element.getElementsByTagName(STRONG_ENTITY_TAG);
        Element strongEntityElement;

        strongEntityElement = (Element) strongEntitiesElements.item(0);
        Assert.assertEquals(entity0.getName(), strongEntityElement.getAttribute(NAME_ATTRIBUTE));
        Assert.assertEquals(entity0.getId().toString(), strongEntityElement.getAttribute(ID_ATTRIBUTE));

        strongEntityElement = (Element) strongEntitiesElements.item(1);
        Assert.assertEquals(entity1.getName(), strongEntityElement.getAttribute(NAME_ATTRIBUTE));
        Assert.assertEquals(entity1.getId().toString(), strongEntityElement.getAttribute(ID_ATTRIBUTE));

        strongEntityElement = (Element) strongEntitiesElements.item(2);
        Assert.assertEquals(entity2.getName(), strongEntityElement.getAttribute(NAME_ATTRIBUTE));
        Assert.assertEquals(entity2.getId().toString(), strongEntityElement.getAttribute(ID_ATTRIBUTE));

        strongEntityElement = (Element) strongEntitiesElements.item(3);
        Assert.assertEquals(entity3.getName(), strongEntityElement.getAttribute(NAME_ATTRIBUTE));
        Assert.assertEquals(entity3.getId().toString(), strongEntityElement.getAttribute(ID_ATTRIBUTE));

        strongEntityElement = (Element) strongEntitiesElements.item(4);
        Assert.assertEquals(entity4.getName(), strongEntityElement.getAttribute(NAME_ATTRIBUTE));
        Assert.assertEquals(entity4.getId().toString(), strongEntityElement.getAttribute(ID_ATTRIBUTE));
    }

    @Test
    public void testGetStrongEntityCollectionFromElement() {
        String xml = "<diagram>\n" +
                "  <strongEntities>\n" +
                "    <strongEntity id=\"16c094cd-593f-4299-9369-ceefde93a4b0\" name=\"TestEntity0\"/>\n" +
                "    <strongEntity id=\"5caa571d-ad88-41ce-9c83-ec276c2e1fdb\" name=\"TestEntity1\"/>\n" +
                "    <strongEntity id=\"cc6f60b6-8564-4670-86ee-56689b3839a9\" name=\"TestEntity2\"/>\n" +
                "    <strongEntity id=\"136514a4-3d1e-41dd-9a46-a665ca6b0d68\" name=\"TestEntity3\"/>\n" +
                "    <strongEntity id=\"5630283e-a4b0-490f-8b96-1c27e27c709d\" name=\"TestEntity4\"/>\n" +
                "  </strongEntities>\n" +
                "</diagram>\n";

        Document document = null;
        try {
            document = TestUtilities.loadXMLFromString(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert document != null;
        StrongEntityCollection strongEntityCollection = new StrongEntitiesXmlManager().getItemFromXmlElement(document.getDocumentElement());

        IStrongEntity strongEntity = strongEntityCollection.getStrongEntity(UUID.fromString("16c094cd-593f-4299-9369-ceefde93a4b0"));
        Assert.assertNotNull(strongEntity);
        Assert.assertEquals("TestEntity0", strongEntity.getName());

        strongEntity = strongEntityCollection.getStrongEntity(UUID.fromString("5caa571d-ad88-41ce-9c83-ec276c2e1fdb"));
        Assert.assertNotNull(strongEntity);
        Assert.assertEquals("TestEntity1", strongEntity.getName());

        strongEntity = strongEntityCollection.getStrongEntity(UUID.fromString("cc6f60b6-8564-4670-86ee-56689b3839a9"));
        Assert.assertNotNull(strongEntity);
        Assert.assertEquals("TestEntity2", strongEntity.getName());

        strongEntity = strongEntityCollection.getStrongEntity(UUID.fromString("136514a4-3d1e-41dd-9a46-a665ca6b0d68"));
        Assert.assertNotNull(strongEntity);
        Assert.assertEquals("TestEntity3", strongEntity.getName());

        strongEntity = strongEntityCollection.getStrongEntity(UUID.fromString("5630283e-a4b0-490f-8b96-1c27e27c709d"));
        Assert.assertNotNull(strongEntity);
        Assert.assertEquals("TestEntity4", strongEntity.getName());
    }
}
