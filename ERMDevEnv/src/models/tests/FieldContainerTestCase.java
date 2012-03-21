package models.tests;


import infrastructure.IterableExtensions;
import junit.framework.Assert;
import models.Entity;
import models.Field;
import models.FieldContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FieldContainerTestCase {

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
	public void testCannotAddTwoFieldsWithSameNameAtSameLevel()
	{
		FieldContainer fieldContainer = new Entity("EntityName");
		Assert.assertTrue(fieldContainer.addField("Field1"));
		Assert.assertFalse(fieldContainer.addField("Field1"));
	}
	
	@Test
	public void testCanRemoveFieldUsingName()
	{
		FieldContainer fieldContainer = new Entity("EntityName");
		Assert.assertTrue(fieldContainer.addField("Field1"));
		Assert.assertTrue(fieldContainer.addField("Field2"));
		Assert.assertEquals(2, IterableExtensions.count(fieldContainer.getFields()));
		Assert.assertTrue(fieldContainer.removeField("Field1"));
		Assert.assertEquals(1, IterableExtensions.count(fieldContainer.getFields()));
	}
	
	@Test
	public void testCannotRemoveIfFieldDoesNotExists()
	{
		FieldContainer fieldContainer = new Entity("EntityName");
		Assert.assertTrue(fieldContainer.addField("Field1"));
		Assert.assertTrue(fieldContainer.addField("Field2"));
		Assert.assertEquals(2, IterableExtensions.count(fieldContainer.getFields()));
		Assert.assertFalse(fieldContainer.removeField("Field3"));
		Assert.assertEquals(2, IterableExtensions.count(fieldContainer.getFields()));
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
