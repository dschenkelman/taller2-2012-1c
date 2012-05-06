package persistence.tests;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import models.Attribute;
import models.AttributeCollection;
import models.AttributeType;
import models.Cardinality;
import models.IdGroupCollection;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import persistence.AttributeCollectionXmlManager;
import persistence.HierarchyCollectionXmlManager;
import persistence.XmlFileManager;

public class AttributeCollectionXMLManagerTestCase {

    private String xmlPath = "test.xml";


    @Test
    public void testCreateElementOfAttributeCollection() {
        AttributeCollection attCol = new AttributeCollection();
        AttributeCollection attCol1 = new AttributeCollection();
        Attribute attribute1 = null;
        try {
            attCol.addAttribute("SimpleAttribute1");
            attCol.addAttribute("SimpleAttribute2", true, new Cardinality(1, 10), new IdGroupCollection(),
                    AttributeType.characterization, null);
            attribute1 = new Attribute("ComplexAttribute", true, new Cardinality(0, 10), new IdGroupCollection(),
                    AttributeType.calculated, new String("expression1"));

            attCol1.addAttribute("SimpleAttribute3");
            attCol1.addAttribute("SimpleAttribute4");
            attribute1.setAttributes(attCol1);
            attCol.addAttribute(attribute1);

        } catch (Exception e) {
            fail();
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

        Element attributes = new AttributeCollectionXmlManager().getElementFromItem(attCol, document);
        diagram.appendChild(attributes);

        new XmlFileManager().write(document, xmlPath);

        //TEST VALUES OF XML NODES

        NodeList attributeElements = attributes.getElementsByTagName("attribute");
        Element attributeElem = (Element) attributeElements.item(0);
        assertEquals(attributeElem.getAttribute("name"), "SimpleAttribute1");

        attributeElem = (Element) attributeElements.item(1);
        assertEquals(attributeElem.getAttribute("name"), "SimpleAttribute2");
        assertTrue(Double.valueOf(attributeElem.getAttribute("minimumCardinality")) == 1);
        assertTrue(Double.valueOf(attributeElem.getAttribute("maximumCardinality")) == 10);
        assertEquals(attributeElem.getElementsByTagName("idGroups").item(0).getNodeName(), "idGroups");
        assertEquals(attributeElem.getAttribute("type"), AttributeType.characterization.toString());

        attributeElem = (Element) attributeElements.item(2);
        assertEquals(attributeElem.getAttribute("name"), "ComplexAttribute");
        assertTrue(Double.valueOf(attributeElem.getAttribute("minimumCardinality")) == 0);
        assertTrue(Double.valueOf(attributeElem.getAttribute("maximumCardinality")) == 10);
        assertEquals(attributeElem.getElementsByTagName("idGroups").item(0).getNodeName(), "idGroups");
        assertEquals(attributeElem.getAttribute("type"), AttributeType.calculated.toString());

        NodeList attElems = ((Element) attributeElem.getElementsByTagName("attributes").item(0)).getElementsByTagName("attribute");


        assertEquals(((Element) attElems.item(0)).getAttribute("name"), "SimpleAttribute3");
        assertEquals(((Element) attElems.item(1)).getAttribute("name"), "SimpleAttribute4");

    }

    @Test
    public void testCreateHierarchyCollectionFromXml() {
        String xml =

                "<attributes>" +
                        "<attribute id=\"63fbe9a3-a337-431b-b18c-24c2fe45f438\" maximumCardinality=\"10.0\" minimumCardinality=\"1.0\" " +
                        "name=\"SimpleAttribute1\" type=\"characterization\" isKeyField=\"true\">" +
                            "<idGroups>\n" +
                                "<idGroup number=\"0\"/>\n" +
                                "<idGroup number=\"1\"/>\n" +
                                "<idGroup number=\"2\"/>\n" +
                                "<idGroup number=\"3\"/>\n" +
                                "<idGroup number=\"4\"/>\n" +
                                "<idGroup number=\"5\"/>\n" +
                                "<idGroup number=\"6\"/>\n" +
                                "<idGroup number=\"7\"/>\n" +
                                "<idGroup number=\"8\"/>\n" +
                                "<idGroup number=\"9\"/>\n" +
                            "</idGroups>" +
                        "</attribute>" +
                        "<attribute expression=\"expression1\" id=\"46f4c7c7-ab54-4afc-bfa8-87fa0f27c09b\" maximumCardinality=\"10.0\" " +
                            "minimumCardinality=\"0.0\" name=\"ComplexAttribute\" type=\"calculated\" isKeyField=\"false\">" +
                            "<attributes>" +
                                "<attribute id=\"a835f4cc-c85d-4606-996a-93b89b36ae34\" name=\"SimpleAttribute2\" isKeyField=\"false\">" +
                                    "<idGroups>\n" +
                                        "<idGroup number=\"10\"/>\n" +
                                        "<idGroup number=\"11\"/>\n" +
                                        "<idGroup number=\"12\"/>\n" +
                                        "<idGroup number=\"13\"/>\n" +
                                        "<idGroup number=\"14\"/>\n" +
                                        "<idGroup number=\"15\"/>\n" +
                                        "<idGroup number=\"16\"/>\n" +
                                        "<idGroup number=\"17\"/>\n" +
                                        "<idGroup number=\"18\"/>\n" +
                                        "<idGroup number=\"19\"/>\n" +
                                    "</idGroups>" +
                                "</attribute>" +
                                "<attribute id=\"a835f4cc-c85d-4606-996a-93b89b36ae35\" name=\"SimpleAttribute3\" isKeyField=\"true\"/>" +
                            "</attributes>" +
                            "<idGroups>\n" +
                                "<idGroup number=\"20\"/>\n" +
                                "<idGroup number=\"21\"/>\n" +
                                "<idGroup number=\"22\"/>\n" +
                                "<idGroup number=\"23\"/>\n" +
                                "<idGroup number=\"24\"/>\n" +
                                "<idGroup number=\"25\"/>\n" +
                                "<idGroup number=\"26\"/>\n" +
                                "<idGroup number=\"27\"/>\n" +
                                "<idGroup number=\"28\"/>\n" +
                                "<idGroup number=\"29\"/>\n" +
                            "</idGroups>" +
                        "</attribute>" +
                        "<attribute id=\"a835f4cc-c85d-4606-996a-93b89b36ae36\" name=\"SimpleAttribute4\" isKeyField=\"true\"/>" +
                "</attributes>";

        Document document = null;
        try {
            document = TestUtilities.loadXMLFromString(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AttributeCollection attCol = null;
        try {
            attCol = new AttributeCollectionXmlManager().getItemFromXmlElement(document.getDocumentElement());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        Iterator<Attribute> ite = attCol.iterator();
        Attribute att1 = ite.next();
        Attribute att2 = ite.next();
        Attribute att3 = ite.next();

        //Valido el attributo 1
        assertEquals(att1.getName(), "SimpleAttribute1");
        assertEquals(att1.getId().toString(), "63fbe9a3-a337-431b-b18c-24c2fe45f438");
        assertTrue(att1.getCardinality().getMinimum() == 1);
        assertTrue(att1.getCardinality().getMaximum() == 10);
        assertTrue(att1.getType() == AttributeType.characterization);
        assertTrue(att1.getIdGroup() != null);
        for (int i = 0; i < 10; i++)
            Assert.assertTrue(att1.getIdGroup().exists(i));
        for (int i = 10; i < 20; i++)
            Assert.assertFalse(att1.getIdGroup().exists(i));
        for (int i = 20; i < 30; i++)
            Assert.assertFalse(att1.getIdGroup().exists(i));
        assertTrue(att1.isKey());

        //Valido el attributo 2
        assertEquals(att2.getName(), "ComplexAttribute");
        assertEquals(att2.getId().toString(), "46f4c7c7-ab54-4afc-bfa8-87fa0f27c09b");
        assertTrue(att2.getCardinality().getMinimum() == 0);
        assertTrue(att2.getCardinality().getMaximum() == 10);
        assertTrue(att2.getType() == AttributeType.calculated);
        assertTrue(att2.getIdGroup() != null);
        for (int i = 20; i < 30; i++)
            Assert.assertTrue(att2.getIdGroup().exists(i));
        for (int i = 0; i < 10; i++)
            Assert.assertFalse(att2.getIdGroup().exists(i));
        for (int i = 10; i < 20; i++)
            Assert.assertFalse(att2.getIdGroup().exists(i));
        assertEquals(att2.getExpression(), "expression1");
        assertFalse(att2.isKey());

        assertTrue(att2.getAttributes().count() == 2);
        ite = att2.getAttributes().iterator();
        Attribute subAtt1 = ite.next();
        Attribute subAtt2 = ite.next();
        assertEquals(subAtt1.getName(), "SimpleAttribute2");
        assertTrue(subAtt1.getIdGroup() != null);
        for (int i = 20; i < 30; i++)
            Assert.assertFalse(subAtt1.getIdGroup().exists(i));
        for (int i = 0; i < 10; i++)
            Assert.assertFalse(subAtt1.getIdGroup().exists(i));
        for (int i = 10; i < 20; i++)
            Assert.assertTrue(subAtt1.getIdGroup().exists(i));
        assertFalse(subAtt1.isKey());

        assertEquals(subAtt2.getName(), "SimpleAttribute3");
        for (int i = 20; i < 30; i++)
            Assert.assertFalse(subAtt2.getIdGroup().exists(i));
        for (int i = 0; i < 10; i++)
            Assert.assertFalse(subAtt2.getIdGroup().exists(i));
        for (int i = 10; i < 20; i++)
            Assert.assertFalse(subAtt2.getIdGroup().exists(i));        assertTrue(subAtt2.isKey());

        //Valido el attributo 3
        assertEquals(att3.getName(), "SimpleAttribute4");
        assertEquals(att3.getId().toString(), "a835f4cc-c85d-4606-996a-93b89b36ae36");
        assertTrue(att3.isKey());

    }
}
