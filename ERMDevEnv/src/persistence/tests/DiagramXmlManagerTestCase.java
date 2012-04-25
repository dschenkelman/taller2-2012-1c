package persistence.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import junit.framework.Assert;

import models.DiagramType;
import models.EntityCollection;
import models.Hierarchy;
import models.HierarchyCollection;
import models.Relationship;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import persistence.DiagramXmlManager;


public class DiagramXmlManagerTestCase {

	@Test
	public void testShouldGenerateAXmlFromDiagram() throws Exception
	{
		EntityCollection entities = new EntityCollection();
		List<Relationship> relationships = new ArrayList<Relationship>();
		HierarchyCollection hierarchies = new HierarchyCollection();
		List<DiagramType> subDiagrams = new ArrayList<DiagramType>();
		DiagramType subDiagram1 = new DiagramType();
		DiagramType subDiagram2 = new DiagramType();
		
		entities.add("entity1");
		entities.add("entity2");
		relationships.add(new Relationship(UUID.randomUUID(), "relation1", true));
		relationships.add(new Relationship(UUID.randomUUID(), "relation2", false));
		Hierarchy hierarchy = hierarchies.createHierarchy(entities.get("entity1").getId(), true, true);
		hierarchies.addChild(hierarchy.getUUID(), entities.get("entity2").getId());
		subDiagrams.add(subDiagram1);
		subDiagrams.add(subDiagram2);
		
		DiagramType diagram = new DiagramType(entities, relationships, hierarchies, subDiagrams);
		
		Document document = TestUtilities.createDocument();
		
		DiagramXmlManager xmlManager = new DiagramXmlManager();
		
		Element diagramElement = xmlManager.getElementFromItem(diagram, document);
		
		Assert.assertEquals("diagram", diagramElement.getTagName());
		Assert.assertEquals(diagram.getId().toString(), diagramElement.getAttribute("id"));
		
		Element entitiesElement = (Element) diagramElement.getElementsByTagName("entities").item(0);
		Element relationshipsElement = (Element) diagramElement.getElementsByTagName("relationships").item(0);
		Element hierarchiesElement = (Element) diagramElement.getElementsByTagName("hierarchies").item(0);
		Element subDiagramsElement = (Element) diagramElement.getElementsByTagName("subDiagrams").item(0);
		
		NodeList items = entitiesElement.getChildNodes();
		Assert.assertEquals(2, items.getLength());
		Assert.assertEquals("entity1", ((Element) items.item(0)).getAttribute("name"));
		Assert.assertEquals("entity2", ((Element) items.item(1)).getAttribute("name"));
		
		items = hierarchiesElement.getChildNodes();
		Assert.assertEquals(1, items.getLength());
		Assert.assertEquals(hierarchy.getUUID().toString(), ((Element) items.item(0)).getAttribute("id"));
		
		NodeList relationsList = relationshipsElement.getElementsByTagName("relationship");
		NodeList subDiagList = subDiagramsElement.getElementsByTagName("diagram");
		
		Assert.assertEquals(2, relationsList.getLength());					
		Assert.assertEquals("relation1", ((Element) relationsList.item(0)).getAttribute("name"));
		Assert.assertEquals("relation2", ((Element) relationsList.item(1)).getAttribute("name"));
		
		Assert.assertEquals(2, subDiagList.getLength());
		Assert.assertEquals(subDiagram1.getId().toString(), ((Element) subDiagList.item(0)).getAttribute("id"));
		Assert.assertEquals(subDiagram2.getId().toString(), ((Element) subDiagList.item(1)).getAttribute("id"));
	}
	
	@Test
	public void testShouldGenerateADiagramFromXml() throws Exception
	{
		
	}
}
