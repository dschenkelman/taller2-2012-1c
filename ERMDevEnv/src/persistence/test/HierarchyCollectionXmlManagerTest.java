package persistence.test;

import junit.framework.Assert;
import models.Hierarchy;
import models.HierarchyCollection;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import persistence.HierarchyCollectionXmlManager;
import persistence.XmlManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.UUID;

public class HierarchyCollectionXmlManagerTest {

    private static String PATH = "HierarchyCollectionXmlManagerTest.xml";

    @Test
    public void testCreateElementOfHierarchyCollection() {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();
        UUID generalEntityUUID = UUID.randomUUID();
        try {
            hierarchyCollection.createHierarchy(generalEntityUUID, false, true);
            hierarchyCollection.createHierarchy(UUID.randomUUID(), true, true);
            hierarchyCollection.createHierarchy(UUID.randomUUID(), false, false);
            hierarchyCollection.createHierarchy(UUID.randomUUID(), true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            hierarchyCollection.addChild(generalEntityUUID, UUID.randomUUID());
            hierarchyCollection.addChild(generalEntityUUID, UUID.randomUUID());
            hierarchyCollection.addChild(generalEntityUUID, UUID.randomUUID());
            hierarchyCollection.addChild(generalEntityUUID, UUID.randomUUID());
            hierarchyCollection.addChild(generalEntityUUID, UUID.randomUUID());
        } catch (Exception e) {
            e.printStackTrace();
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

        Element hierarchies = HierarchyCollectionXmlManager.getHierarchiesElementFromHierarchyCollection(hierarchyCollection, document);
        diagram.appendChild(hierarchies);

        XmlManager.writeToFile(document, PATH);
    }

    @Test
    public void testCreateHierarchyCollectionFromXml() {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();
        UUID generalEntityUUID = UUID.randomUUID();
        UUID generalEntityUUID1 = UUID.randomUUID();
        UUID generalEntityUUID2 = UUID.randomUUID();
        UUID generalEntityUUID3 = UUID.randomUUID();
        try {
            hierarchyCollection.createHierarchy(generalEntityUUID, false, true);
            hierarchyCollection.createHierarchy(generalEntityUUID1, true, true);
            hierarchyCollection.createHierarchy(generalEntityUUID2, false, false);
            hierarchyCollection.createHierarchy(generalEntityUUID3, true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UUID child1 = UUID.randomUUID();
        UUID child2 = UUID.randomUUID();
        UUID child3 = UUID.randomUUID();
        UUID child4 = UUID.randomUUID();
        try {
            hierarchyCollection.addChild(generalEntityUUID, child1);
            hierarchyCollection.addChild(generalEntityUUID, child2);
            hierarchyCollection.addChild(generalEntityUUID, child3);
            hierarchyCollection.addChild(generalEntityUUID, child4);
        } catch (Exception e) {
            e.printStackTrace();
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

        Element hierarchies = HierarchyCollectionXmlManager.getHierarchiesElementFromHierarchyCollection(hierarchyCollection, document);
        diagram.appendChild(hierarchies);

        XmlManager.writeToFile(document, PATH);

        HierarchyCollection hierarchyCollectionFromXml = HierarchyCollectionXmlManager.getHierarchyCollectionFromElement(XmlManager.readXml(PATH).getDocumentElement());

        Assert.assertEquals(generalEntityUUID, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID).getGeneralEntityUUID());
        Assert.assertEquals(false, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID).isExclusive());
        Assert.assertEquals(true, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID).isTotal());

        Assert.assertEquals(generalEntityUUID1, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID1).getGeneralEntityUUID());
        Assert.assertEquals(true, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID1).isExclusive());
        Assert.assertEquals(true, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID1).isTotal());

        Assert.assertEquals(generalEntityUUID2, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID2).getGeneralEntityUUID());
        Assert.assertEquals(false, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID2).isExclusive());
        Assert.assertEquals(false, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID2).isTotal());

        Assert.assertEquals(generalEntityUUID3, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID3).getGeneralEntityUUID());
        Assert.assertEquals(true, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID3).isExclusive());
        Assert.assertEquals(false, hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID3).isTotal());

        Hierarchy hierarchy = hierarchyCollectionFromXml.getHierarchyWithGeneralEntityUUID(generalEntityUUID);
        Assert.assertEquals(true, hierarchy.hasChild(child1));
        Assert.assertEquals(true, hierarchy.hasChild(child2));
        Assert.assertEquals(true, hierarchy.hasChild(child3));
        Assert.assertEquals(true, hierarchy.hasChild(child4));
        Assert.assertEquals(false, hierarchy.hasChild(UUID.randomUUID()));
    }

}
