package models.tests;

import models.Entity;
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

	@Before
	public void setUp() throws Exception 
	{
	}

	@After
	public void tearDown() throws Exception 
	{
	}
}