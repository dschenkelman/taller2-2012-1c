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
        Hierarchy hierarchy;
        hierarchy = hierarchyCollection.createHierarchy(generalEntityUUID, false, true);
        hierarchyCollection.createHierarchy(UUID.randomUUID(), true, true);
        hierarchyCollection.createHierarchy(UUID.randomUUID(), false, false);
        hierarchyCollection.createHierarchy(UUID.randomUUID(), true, false);

        UUID uuid = hierarchy.getUUID();
        try {
            hierarchyCollection.addChild(uuid, UUID.randomUUID());
            hierarchyCollection.addChild(uuid, UUID.randomUUID());
            hierarchyCollection.addChild(uuid, UUID.randomUUID());
            hierarchyCollection.addChild(uuid, UUID.randomUUID());
            hierarchyCollection.addChild(uuid, UUID.randomUUID());
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
        Hierarchy hierarchy = null;
        hierarchy = hierarchyCollection.createHierarchy(generalEntityUUID, false, true);
        hierarchyCollection.createHierarchy(generalEntityUUID1, true, true);
        hierarchyCollection.createHierarchy(generalEntityUUID2, false, false);
        hierarchyCollection.createHierarchy(generalEntityUUID3, true, false);

        UUID uuid = hierarchy.getUUID();
        UUID child1 = UUID.randomUUID();
        UUID child2 = UUID.randomUUID();
        UUID child3 = UUID.randomUUID();
        UUID child4 = UUID.randomUUID();
        try {
            hierarchyCollection.addChild(uuid, child1);
            hierarchyCollection.addChild(uuid, child2);
            hierarchyCollection.addChild(uuid, child3);
            hierarchyCollection.addChild(uuid, child4);
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

        Assert.assertEquals(generalEntityUUID, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID).iterator().next().getGeneralEntityUUID());
        Assert.assertEquals(false, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID).iterator().next().isExclusive());
        Assert.assertEquals(true, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID).iterator().next().isTotal());

        Assert.assertEquals(generalEntityUUID1, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID1).iterator().next().getGeneralEntityUUID());
        Assert.assertEquals(true, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID1).iterator().next().isExclusive());
        Assert.assertEquals(true, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID1).iterator().next().isTotal());

        Assert.assertEquals(generalEntityUUID2, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID2).iterator().next().getGeneralEntityUUID());
        Assert.assertEquals(false, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID2).iterator().next().isExclusive());
        Assert.assertEquals(false, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID2).iterator().next().isTotal());

        Assert.assertEquals(generalEntityUUID3, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID3).iterator().next().getGeneralEntityUUID());
        Assert.assertEquals(true, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID3).iterator().next().isExclusive());
        Assert.assertEquals(false, hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID3).iterator().next().isTotal());

        hierarchy = hierarchyCollectionFromXml.getHierarchiesWithGeneralEntityUUID(generalEntityUUID).iterator().next();
        Assert.assertEquals(true, hierarchy.hasChild(child1));
        Assert.assertEquals(true, hierarchy.hasChild(child2));
        Assert.assertEquals(true, hierarchy.hasChild(child3));
        Assert.assertEquals(true, hierarchy.hasChild(child4));
        Assert.assertEquals(false, hierarchy.hasChild(UUID.randomUUID()));
    }

}
