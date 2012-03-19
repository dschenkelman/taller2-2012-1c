package models.tests;

import models.Entity;
import junit.framework.Assert;
import junit.framework.TestCase;

public class EntityTestCase extends TestCase {

	public EntityTestCase(String arg0) {
		super(arg0);
	}
	
	public void testCreatingEntityProvidesName()
	{
		Entity entity = new Entity("EntityName");
		Assert.assertEquals("EntityName", entity.getName());
	}
	
	public void testNewEntitiesHaveNoRelationshipsNorFields()
	{
		Entity entity = new Entity("EntityName");
		
		Assert.assertEquals(0, entity.getFields().size());
		Assert.assertEquals(0, entity.getRelationships().size());
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
