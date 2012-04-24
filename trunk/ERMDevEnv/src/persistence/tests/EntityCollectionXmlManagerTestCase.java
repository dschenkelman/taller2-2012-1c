package persistence.tests;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import models.Entity;
import models.EntityCollection;
import models.EntityType;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import persistence.EntityCollectionXmlManager;


public class EntityCollectionXmlManagerTestCase {

	@Test
	public void testShouldGenerateAnXmlFromEntityCollection() throws Exception
	{
		EntityCollection collection = new EntityCollection();
		
		Entity entity1 = new Entity("Entity1", EntityType.Domain);
		Entity entity2 = new Entity("Entity2", EntityType.Thing);
		
		collection.add(entity1);
		collection.add(entity2);
		
		Document document = TestUtilities.createDocument();
		EntityCollectionXmlManager xmlManager = new EntityCollectionXmlManager();
		
		Element element = xmlManager.getElementFromItem(collection, document);
		
		Assert.assertEquals("entities", element.getTagName());
		
		NodeList items = element.getChildNodes();
		Assert.assertEquals(2, items.getLength()); 
		
		Assert.assertEquals("entity", ((Element)items.item(0)).getTagName());
		Assert.assertEquals(entity1.getName(), ((Element)items.item(0)).getAttribute("name"));
		Assert.assertEquals(entity2.getName(), ((Element)items.item(1)).getAttribute("name"));
		Assert.assertEquals(entity1.getType().toString(), ((Element)items.item(0)).getAttribute("type"));
		Assert.assertEquals(entity2.getType().toString(), ((Element)items.item(1)).getAttribute("type"));
		Assert.assertEquals(entity1.getId().toString(), ((Element)items.item(0)).getAttribute("id"));
		Assert.assertEquals(entity2.getId().toString(), ((Element)items.item(1)).getAttribute("id"));
	}
	
	@Test
	public void testShouldGenetarteAnEntityCollectionFromXml()
	{
		
		
		
	}
}
