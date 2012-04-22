package persistence.tests;

import static org.junit.Assert.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.Attribute;
import models.AttributeCollection;
import models.AttributeType;
import models.Cardinality;
import models.IdGroupCollection;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.AttributeCollectionXmlManager;
import persistence.HierarchyCollectionXmlManager;
import persistence.XmlManager;

public class AttributeCollectionXMLManager {

	private String xmlPath="";
	
	
	@Test
    public void testCreateElementOfAttributeCollection()  {
		AttributeCollection attCol = new AttributeCollection ();
		try {
			attCol.addAttribute("Attribute1");
			attCol.addAttribute("Attribute2", true, new Cardinality(1,10), new IdGroupCollection(), 
					AttributeType.characterization, null);
			attCol.addAttribute("Attribute3");
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
	     
	     Element attributes = AttributeCollectionXmlManager.getAttributesElementFromHierarchyCollection(attCol, document);
	     diagram.appendChild(attributes);

	     XmlManager.writeToFile(document, xmlPath);
	}
	
	@Test
	public void testCreateHierarchyCollectionFromXml() {
		 
	}
}
