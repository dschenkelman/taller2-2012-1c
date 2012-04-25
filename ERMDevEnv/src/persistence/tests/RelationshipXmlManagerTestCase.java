package persistence.tests;


import infrastructure.Func;
import infrastructure.IterableExtensions;
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
	public void testShouldGenerateXmlElementFromRelationship() throws Exception{
		
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
		Assert.assertEquals("RelationshipName", element.getAttribute("name"));
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
	
	@Test
	public void testShouldGenerateRelationshipFromXml() throws Exception{
		String xml = "<relationships><relationship id='01854049-A762-4392-9357-A213C4110220' " +
				"name='Relationship' composition='true'><entities>" +
				"<entity entityId='0E6A2A75-A645-4665-85C8-21179BF362B8' minimumCardinality='0'" +
				" maximumCardinality='1' role='Role1' />" +
				"<entity entityId='0E6A2A75-A645-4665-85C8-21179BF362B7' minimumCardinality='0'" +
				" maximumCardinality='*' role='Role2' />" +
				"<entity entityId='0E6A2A75-A645-4665-85C8-21179BF362B6' minimumCardinality='*'" +
				" maximumCardinality='*' role='Role3' />" +
				"<entity entityId='0E6A2A75-A645-4665-85C8-21179BF362B5' minimumCardinality='2'" +
				" maximumCardinality='7' role='Role4' />" +
				"</entities></relationship></relationships>";
		
		Document document = TestUtilities.loadXMLFromString(xml);
		Element relationshipElement = (Element) document.getElementsByTagName("relationship").item(0);
		
		RelationshipXmlManager xmlManager = new RelationshipXmlManager();
		
		Relationship relationship = xmlManager.getItemFromXmlElement(relationshipElement);
		
		Assert.assertEquals("01854049-a762-4392-9357-a213c4110220", relationship.getId().toString());
		Assert.assertEquals("Relationship", relationship.getName());
		Assert.assertTrue(relationship.isComposition());
		
		Iterable<RelationshipEntity> relationshipEntities = relationship.getRelationshipEntities();
		Assert.assertEquals(4, IterableExtensions.count(relationshipEntities));
		
		Func<RelationshipEntity, String, Boolean> cmpFunc = new Func<RelationshipEntity, String, Boolean>(){

			@Override
			public Boolean execute(RelationshipEntity relationshipEntity,
					String id) {
				return relationshipEntity.getEntityId().toString().equalsIgnoreCase(id);
			};
		};
		
		RelationshipEntity relationshipEntity1 = IterableExtensions.firstOrDefault(relationshipEntities,
				cmpFunc, "0E6A2A75-A645-4665-85C8-21179BF362B8");
		
		Assert.assertEquals("0e6a2a75-a645-4665-85c8-21179bf362b8", relationshipEntity1.getEntityId().toString());
		Assert.assertEquals(1.0, relationshipEntity1.getCardinality().getMaximum());
		Assert.assertEquals(0.0, relationshipEntity1.getCardinality().getMinimum());
		Assert.assertEquals("Role1", relationshipEntity1.getRole());
		
		RelationshipEntity relationshipEntity2 = IterableExtensions.firstOrDefault(relationshipEntities,
				cmpFunc, "0E6A2A75-A645-4665-85C8-21179BF362B7");
		
		Assert.assertEquals("0e6a2a75-a645-4665-85c8-21179bf362b7", relationshipEntity2.getEntityId().toString());
		Assert.assertEquals(Double.POSITIVE_INFINITY, relationshipEntity2.getCardinality().getMaximum());
		Assert.assertEquals(0.0, relationshipEntity2.getCardinality().getMinimum());
		Assert.assertEquals("Role2", relationshipEntity2.getRole());
		
		RelationshipEntity relationshipEntity3 = IterableExtensions.firstOrDefault(relationshipEntities,
				cmpFunc, "0E6A2A75-A645-4665-85C8-21179BF362B6");
		
		Assert.assertEquals("0e6a2a75-a645-4665-85c8-21179bf362b6", relationshipEntity3.getEntityId().toString());
		Assert.assertEquals(Double.POSITIVE_INFINITY, relationshipEntity3.getCardinality().getMaximum());
		Assert.assertEquals(Double.POSITIVE_INFINITY, relationshipEntity3.getCardinality().getMinimum());
		Assert.assertEquals("Role3", relationshipEntity3.getRole());
		
		RelationshipEntity relationshipEntity4 = IterableExtensions.firstOrDefault(relationshipEntities,
				cmpFunc, "0E6A2A75-A645-4665-85C8-21179BF362B5");
		
		Assert.assertEquals("0e6a2a75-a645-4665-85c8-21179bf362b5", relationshipEntity4.getEntityId().toString());
		Assert.assertEquals(7.0, relationshipEntity4.getCardinality().getMaximum());
		Assert.assertEquals(2.0, relationshipEntity4.getCardinality().getMinimum());
		Assert.assertEquals("Role4", relationshipEntity4.getRole());
	}
	
	@Before
	public void setUp() throws Exception {
	}

}
