package models.tests;

import junit.framework.Assert;
import models.Cardinality;
import models.Entity;
import models.RelationshipEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RelationshipEntityTestCase {

	@Test
	public void testCreatingLeavesCardinalitiesAndRoleAsNull()
	{
		RelationshipEntity relationshipEntity = 
			new RelationshipEntity(new Entity("TestName"));
	
		Assert.assertEquals("TestName",relationshipEntity.getEntity().getName());
		Assert.assertNull(relationshipEntity.getCardinality());
		Assert.assertNull(relationshipEntity.getRole());
	}
	
	@Test
	public void testCreatingAnCompleteRelationshipEntity() throws Exception
	{
		RelationshipEntity relationshipEntity = 
			new RelationshipEntity(new Entity("TestName"), 
					new Cardinality(0,1), "role");
		
		Assert.assertEquals("TestName",relationshipEntity.getEntity().getName());
		Assert.assertEquals(0.0,relationshipEntity.getCardinality().getMinimum());
		Assert.assertEquals(1.0,relationshipEntity.getCardinality().getMaximum());
		Assert.assertEquals("role",relationshipEntity.getRole());
	}
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
}
