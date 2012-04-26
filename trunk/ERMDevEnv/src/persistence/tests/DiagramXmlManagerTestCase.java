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
		
		subDiagram1.setEntities(entities);
		subDiagram1.addSubDiagram(subDiagram2);
		
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
		NodeList subDiagList = subDiagramsElement.getChildNodes();
		
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
		String xml = "<diagram id='3F2504E0-4F89-11D3-9A0C-0300FFFFFFFF'><entities>" +
				"<entity name='entity1' id='3F2504E0-4F89-11D3-9A0C-030CFFFFFFFF'/>" +
				"<entity name='entity2' id='3F2504E0-4F89-11D3-9A0C-030DFFFFFFFF'/></entities>" +
				"<relationships><relationship id='3F2504E0-4F89-11D3-9A0C-030CFFFFFFFA' " +
				"composition='true' name='relation1'><entities>" +
				"<entity entityId='3F2504E0-4F89-11D3-9A0C-030CFFFFFFFF'/></entities>" +
				"</relationship></relationships><hierarchies><hierarchy " +
				"total='true' exclusive='true' " +
				"id='3F2504E0-4F89-11D3-9A0C-030CFFFFFFFC' " +
				"generalEntityId='3F2504E0-4F89-11D3-9A0C-030CFFFFFFFF'>" +
				"<specificEntities><entityId>" +
				"3F2504E0-4F89-11D3-9A0C-030DFFFFFFFF</entityId>" +
				"</specificEntities></hierarchy></hierarchies><diagrams>" +
				"<diagram id='3F2504E0-4F89-11D3-9A0C-0301FFFFFFFF'/>" +
				"<diagram id='3F2504E0-4F89-11D3-9A0C-0302FFFFFFFF'/>" +
				"</diagrams></diagram>";
		
		Document document = TestUtilities.loadXMLFromString(xml);
		
		DiagramXmlManager xmlManager = new DiagramXmlManager();
		
		Element diagramElement = document.getDocumentElement();
		
		DiagramType diagram = xmlManager.getItemFromElement(diagramElement);
		
		Assert.assertEquals("3F2504E0-4F89-11D3-9A0C-0300FFFFFFFF", diagram.getId().toString());
		
		Assert.assertEquals(2, diagram.getEntities().count());
		Assert.assertNotNull(diagram.getEntities().get("entity1"));
		Assert.assertNotNull(diagram.getEntities().get("entity2"));
		Assert.assertEquals("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFF", diagram.getEntities().get("entity1").getId().toString());
		Assert.assertEquals("3F2504E0-4F89-11D3-9A0C-030DFFFFFFFF", diagram.getEntities().get("entity2").getId().toString());
		
		Assert.assertEquals(1, diagram.getRelationships().size());
		Assert.assertNotNull(diagram.getRelationship(UUID.fromString("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFA")));
		Assert.assertEquals("relation1",diagram.getRelationship(UUID.fromString("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFA")).getName());
		Assert.assertTrue(diagram.getRelationship(UUID.fromString("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFA")).isComposition());
		
		Assert.assertEquals(1, diagram.getHierarchies().count());
		Assert.assertNotNull(diagram.getHierarchies().getHierarchy(UUID.fromString("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFC")));
		Assert.assertTrue(diagram.getHierarchies().getHierarchy(UUID.fromString("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFC")).isExclusive());
		Assert.assertTrue(diagram.getHierarchies().getHierarchy(UUID.fromString("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFC")).isTotal());
		Assert.assertEquals("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFF", diagram.getHierarchies().getHierarchy(UUID.fromString("3F2504E0-4F89-11D3-9A0C-030CFFFFFFFC")).getGeneralEntityUUID().toString());
		
		Assert.assertEquals(2, diagram.getSubDiagrams());
		Assert.assertNotNull(diagram.getSubDiagram(UUID.fromString("3F2504E0-4F89-11D3-9A0C-0301FFFFFFFF")));
		Assert.assertNotNull(diagram.getSubDiagram(UUID.fromString("3F2504E0-4F89-11D3-9A0C-0302FFFFFFFF")));
		
	}
}
