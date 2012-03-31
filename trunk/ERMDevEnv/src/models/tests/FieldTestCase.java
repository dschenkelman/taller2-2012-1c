package models.tests;


import infrastructure.IterableExtensions;
import junit.framework.Assert;
import models.Attribute;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FieldTestCase 
{
	@Test
	public void testShouldSetNameWhenCreating()
	{
		Attribute attribute = new Attribute("FieldName");
		
		Assert.assertEquals("FieldName", attribute.getName());
		Assert.assertFalse(attribute.isKey());
	}
	
	@Test
	public void testShouldHaveNoCompositeFieldsByDefault()
	{
		Attribute attribute = new Attribute("FieldName");
		
		Assert.assertEquals(0, IterableExtensions.count(attribute.getAttributes()));
	}
	
	@Test
	public void testCanMarkFieldAsKey()
	{
		Attribute attribute = new Attribute("FieldName");
		
		Assert.assertFalse(attribute.isKey());
		attribute.isKey(true);
		Assert.assertTrue(attribute.isKey());
	}
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
