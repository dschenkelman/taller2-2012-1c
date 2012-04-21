package persistence.tests;

import java.util.ArrayList;
import java.util.List;

import models.Cardinality;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import persistence.RelationshipCollectionXmlManager;


public class RelationshipCollectionXmlManagerTestCase {

	@Test
	public void testShouldCreateAsManyRelationshipElementAsThereAreInRelationshipCollection() throws Exception{
		RelationshipCollectionXmlManager xmlManager = new RelationshipCollectionXmlManager();
		Document document = TestUtilities.createDocument();
		
		List<Relationship> collection = new ArrayList<Relationship>();
		
		Entity entity1 = new Entity("Entity1");
		Entity entity2 = new Entity("Entity2");
		Entity entity3 = new Entity("Entity3");
		Entity entity4 = new Entity("Entity4");
		
		collection.add(new Relationship(
				new RelationshipEntity(entity1, new Cardinality(0, Double.POSITIVE_INFINITY), ""), 
				new RelationshipEntity(entity2, new Cardinality(1, 3), ""))
		);
		
		collection.add(new Relationship(
				new RelationshipEntity(entity3, new Cardinality(5, Double.POSITIVE_INFINITY), ""), 
				new RelationshipEntity(entity4, new Cardinality(2, 3), ""))
		);
		
		Element element = xmlManager.getElementFromItem(collection, document);
		
		Assert.assertEquals("relationships", element.getTagName());
		
		NodeList relationshipList = element.getElementsByTagName("relationship");
		
		Assert.assertEquals(2, relationshipList.getLength());
		
		Element relationship1 = (Element) relationshipList.item(0);
		Element relationship2 = (Element) relationshipList.item(1);
		
		NodeList entitiesList1 = relationship1.getElementsByTagName("entities");
		
		Assert.assertEquals(1, entitiesList1.getLength());
		
		Node entitiesNode1 = entitiesList1.item(0);
		
		NodeList entities1 = entitiesNode1.getChildNodes();
		
		Assert.assertEquals(2, entities1.getLength());
		
		Element entityElement1 = (Element)entities1.item(0);
		Element entityElement2 = (Element)entities1.item(1);
		
		Assert.assertEquals(entity1.getId().toString(), entityElement1.getAttribute("entityId"));
		Assert.assertEquals(entity2.getId().toString(), entityElement2.getAttribute("entityId"));
		
		NodeList entitiesList2 = relationship2.getElementsByTagName("entities");
		
		Assert.assertEquals(1, entitiesList2.getLength());
		
		Node entitiesNode2 = entitiesList2.item(0);
		
		NodeList entities2 = entitiesNode2.getChildNodes();
		
		Assert.assertEquals(2, entities1.getLength());
		
		Element entityElement3 = (Element)entities2.item(0);
		Element entityElement4 = (Element)entities2.item(1);
		
		Assert.assertEquals(entity3.getId().toString(), entityElement3.getAttribute("entityId"));
		Assert.assertEquals(entity4.getId().toString(), entityElement4.getAttribute("entityId"));
	}
	
}
