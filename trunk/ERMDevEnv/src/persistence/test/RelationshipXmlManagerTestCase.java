package persistence.test;


import javax.xml.parsers.DocumentBuilder;

import junit.framework.Assert;

import models.Cardinality;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import persistence.RelationshipXmlManager;

public class RelationshipXmlManagerTestCase {

	@Test
	public void shouldGenerateXmlElementFromRelationship() throws Exception{
		
		Entity entity1 = new Entity("Entity1");
		Entity entity2 = new Entity("Entity2");
		Entity entity3 = new Entity("Entity3");
		Entity entity4 = new Entity("Entity4");
		
		RelationshipEntity relationshipEntity1 = new RelationshipEntity(entity1,
				new Cardinality(0, 1), "Role1");
		RelationshipEntity relationshipEntity2 = new RelationshipEntity(entity2,
				new Cardinality(0, Double.POSITIVE_INFINITY), "Role2");
		RelationshipEntity relationshipEntity3 = new RelationshipEntity(entity3,
				new Cardinality(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), "Role3");
		RelationshipEntity relationshipEntity4 = new RelationshipEntity(entity4,
				new Cardinality(2, 7), "Role4");
		
		Relationship relationship = new Relationship(relationshipEntity1, relationshipEntity2);
		relationship.setName("RelationshipName");
		relationship.isComposition(false);
		relationship.addRelationshipEntity(relationshipEntity3);
		relationship.addRelationshipEntity(relationshipEntity4);
		
		Document document = TestUtilities.createDocument();
		
		RelationshipXmlManager xmlManager = new RelationshipXmlManager();
		
		Element element = xmlManager.getElementFromItem(relationship, document);
		
		Assert.assertEquals("relationship", element.getTagName());
		Assert.assertEquals(relationship.getId().toString(), element.getAttribute("id"));
		Assert.assertEquals(relationship.getName(), element.getAttribute("name"));
		Assert.assertEquals(relationship.isComposition().toString(), element.getAttribute("composition"));
		
		NodeList entitiesList = element.getElementsByTagName("entities");
		
		Assert.assertEquals(1, entitiesList.getLength());
		
		Node entitiesNode = entitiesList.item(0);
		
		NodeList entities = entitiesNode.getChildNodes();
		
		Assert.assertEquals(4, entities.getLength());
		
		Element entityElement1 = (Element)entities.item(0);
		
		Assert.assertEquals("entity", entityElement1.getTagName());
		Assert.assertEquals(entity1.getId().toString(), entityElement1.getAttribute("entityId"));
		Assert.assertEquals("0", entityElement1.getAttribute("minimumCardinality"));
		Assert.assertEquals("1", entityElement1.getAttribute("maximumCardinality"));
		Assert.assertEquals("Role1", entityElement1.getAttribute("role"));
		
		Element entityElement2 = (Element)entities.item(1);
		
		Assert.assertEquals("entity", entityElement2.getTagName());
		Assert.assertEquals(entity2.getId().toString(), entityElement2.getAttribute("entityId"));
		Assert.assertEquals("0", entityElement2.getAttribute("minimumCardinality"));
		Assert.assertEquals("*", entityElement2.getAttribute("maximumCardinality"));
		Assert.assertEquals("Role2", entityElement2.getAttribute("role"));
		
		Element entityElement3 = (Element)entities.item(2);
		
		Assert.assertEquals("entity", entityElement3.getTagName());
		Assert.assertEquals(entity3.getId().toString(), entityElement3.getAttribute("entityId"));
		Assert.assertEquals("*", entityElement3.getAttribute("minimumCardinality"));
		Assert.assertEquals("*", entityElement3.getAttribute("maximumCardinality"));
		Assert.assertEquals("Role3", entityElement3.getAttribute("role"));
		
		Element entityElement4 = (Element)entities.item(3);
		
		Assert.assertEquals("entity", entityElement4.getTagName());
		Assert.assertEquals(entity4.getId().toString(), entityElement4.getAttribute("entityId"));
		Assert.assertEquals("2", entityElement4.getAttribute("minimumCardinality"));
		Assert.assertEquals("7", entityElement4.getAttribute("maximumCardinality"));
		Assert.assertEquals("Role4", entityElement4.getAttribute("role"));
	}
	
	@Before
	public void setUp() throws Exception {
	}

}
