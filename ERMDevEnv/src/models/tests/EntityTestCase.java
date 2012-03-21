package models.tests;

import infrastructure.IterableExtensions;
import models.Entity;
import models.Field;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntityTestCase 
{
	@Test
	public void testCreatingEntityProvidesName()
	{
		Entity entity = new Entity("EntityName");
		Assert.assertEquals("EntityName", entity.getName());
	}
	
	@Test
	public void testNewEntitiesHaveNoRelationshipsNorFields()
	{
		Entity entity = new Entity("EntityName");
		
		Assert.assertEquals(0, IterableExtensions.count(entity.getFields()));
	}
	
	@Test
	public void testCanAddNewFieldsWithDifferentNames()
	{
		Entity entity = new Entity("EntityName");
		Assert.assertEquals(0, IterableExtensions.count(entity.getFields()));
		
		Assert.assertTrue(entity.addField("Field1"));
		Assert.assertEquals(1, IterableExtensions.count(entity.getFields()));
		Field field1 = entity.getField("Field1");
		Assert.assertEquals("Field1", field1.getName());
		Assert.assertFalse(field1.isKey());
		
		Assert.assertTrue(entity.addField("Field2"));
		Assert.assertEquals(2, IterableExtensions.count(entity.getFields()));
		Field field2 = entity.getField("Field2");
		Assert.assertEquals("Field2", field2.getName());
		Assert.assertFalse(field2.isKey());
	}
	
	@Test
	public void testGetFieldReturnsNullWhenFieldDoesNotExist()
	{
		Entity entity = new Entity("EntityName");
		Assert.assertNull(entity.getField("NonExistantName"));
	}
	
	@Test
	public void testCannotAddTwoFieldsWithSameName()
	{
		Entity entity = new Entity("EntityName");
		Assert.assertTrue(entity.addField("Field1"));
		Assert.assertFalse(entity.addField("Field1"));
	}

	@Before
	public void setUp() throws Exception 
	{
	}

	@After
	public void tearDown() throws Exception 
	{
	}
}