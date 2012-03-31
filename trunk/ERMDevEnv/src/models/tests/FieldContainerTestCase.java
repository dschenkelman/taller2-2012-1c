package models.tests;


import infrastructure.IterableExtensions;
import junit.framework.Assert;
import models.Attribute;
import models.AttributeContainer;
import models.Entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FieldContainerTestCase {

	@Test
	public void testCanAddNewFieldsWithDifferentNames()
	{
		Entity entity = new Entity("EntityName");
		Assert.assertEquals(0, IterableExtensions.count(entity.getAttributes()));
		
		Assert.assertTrue(entity.addField("Field1"));
		Assert.assertEquals(1, IterableExtensions.count(entity.getAttributes()));
		Attribute attribute1 = entity.getField("Field1");
		Assert.assertEquals("Field1", attribute1.getName());
		Assert.assertFalse(attribute1.isKey());
		
		Assert.assertTrue(entity.addField("Field2"));
		Assert.assertEquals(2, IterableExtensions.count(entity.getAttributes()));
		Attribute attribute2 = entity.getField("Field2");
		Assert.assertEquals("Field2", attribute2.getName());
		Assert.assertFalse(attribute2.isKey());
	}
	
	@Test
	public void testGetFieldReturnsNullWhenFieldDoesNotExist()
	{
		Entity entity = new Entity("EntityName");
		Assert.assertNull(entity.getField("NonExistantName"));
	}
	
	@Test
	public void testCannotAddTwoFieldsWithSameNameAtSameLevel()
	{
		AttributeContainer attributeContainer = new Entity("EntityName");
		Assert.assertTrue(attributeContainer.addField("Field1"));
		Assert.assertFalse(attributeContainer.addField("Field1"));
	}
	
	@Test
	public void testCanRemoveFieldUsingName()
	{
		AttributeContainer attributeContainer = new Entity("EntityName");
		Assert.assertTrue(attributeContainer.addField("Field1"));
		Assert.assertTrue(attributeContainer.addField("Field2"));
		Assert.assertEquals(2, IterableExtensions.count(attributeContainer.getAttributes()));
		Assert.assertTrue(attributeContainer.removeField("Field1"));
		Assert.assertEquals(1, IterableExtensions.count(attributeContainer.getAttributes()));
	}
	
	@Test
	public void testCannotRemoveIfFieldDoesNotExists()
	{
		AttributeContainer attributeContainer = new Entity("EntityName");
		Assert.assertTrue(attributeContainer.addField("Field1"));
		Assert.assertTrue(attributeContainer.addField("Field2"));
		Assert.assertEquals(2, IterableExtensions.count(attributeContainer.getAttributes()));
		Assert.assertFalse(attributeContainer.removeField("Field3"));
		Assert.assertEquals(2, IterableExtensions.count(attributeContainer.getAttributes()));
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
