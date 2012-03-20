package models.tests;


import junit.framework.Assert;
import models.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FieldTestCase 
{
	@Test
	public void testShouldSetNameWhenCreating()
	{
		Field field = new Field("FieldName");
		
		Assert.assertEquals("FieldName", field.getName());
		Assert.assertFalse(field.isKey());
	}
	
	@Test
	public void testShouldHaveNoCompositeFieldsByDefault()
	{
		Field field = new Field("FieldName");
		
		Assert.assertEquals(0, field.getChildren().size());
	}
	
	@Test
	public void testCanMarkFieldAsKey()
	{
		Field field = new Field("FieldName");
		
		Assert.assertFalse(field.isKey());
		field.isKey(true);
		Assert.assertTrue(field.isKey());
	}
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
