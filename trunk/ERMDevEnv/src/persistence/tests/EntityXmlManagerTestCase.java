package persistence.tests;


import junit.framework.Assert;
import models.AttributeCollection;
import models.Entity;
import models.EntityType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.EntityXmlManager;

public class EntityXmlManagerTestCase {

	@Test
	public void testShouldGenerateAnXmlElementFromEntity() throws Exception
	{
		AttributeCollection attributes = new AttributeCollection();
		attributes.addAttribute("att1");
		attributes.addAttribute("att2");
		attributes.addAttribute("att3");
		
		Entity entity = new Entity("TestName1", EntityType.Historic, attributes);
		
		Document document = TestUtilities.createDocument();
		
		EntityXmlManager xmlManager = new EntityXmlManager();
		
		Element element = xmlManager.getElementFromItem(entity, document);
		
		Assert.assertEquals("entity", element.getTagName());
		Assert.assertEquals(entity.getName(), element.getAttribute("name"));
		Assert.assertEquals(entity.getId().toString(), element.getAttribute("id"));
		Assert.assertEquals(entity.getType().toString(), element.getAttribute("type"));
	}
	
	@Test
	public void testShouldGenerateAnEntityFromXml() throws Exception
	{
		String xml = "<entities><entity name='TestName' type='Historic' " +
				"id='3F2504E0-4F89-11D3-9A0C-030CFFFFFFFF'></entity></entities>";
		
		Document document = TestUtilities.loadXMLFromString(xml);
		
		EntityXmlManager xmlManager = new EntityXmlManager();
		
		Element entityElement = (Element) document.getElementsByTagName("entity").item(0);
		
		Entity entity = xmlManager.getItemFromXmlElement(entityElement);
		Assert.assertEquals("TestName", entity.getName());
		Assert.assertEquals("Historic", entity.getType().toString());
		Assert.assertEquals("3f2504e0-4f89-11d3-9a0c-030cffffffff", entity.getId().toString());
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
