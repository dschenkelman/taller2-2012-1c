package models.tests;

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
		
		Assert.assertEquals(0, entity.getFields().size());
	}
	
	@Test
	public void testCanAddNewFieldsWithDifferentNames()
	{
		Entity entity = new Entity("EntityName");
		Assert.assertEquals(0, entity.getFields().size());
		
		Assert.assertTrue(entity.addField("Field1"));
		Assert.assertEquals(1, entity.getFields().size());
		Field field1 = entity.getField("Field1");
		Assert.assertEquals("Field1", field1.getName());
		Assert.assertFalse(field1.isKey());
		
		Assert.assertTrue(entity.addField("Field2"));
		Assert.assertEquals(2, entity.getFields().size());
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