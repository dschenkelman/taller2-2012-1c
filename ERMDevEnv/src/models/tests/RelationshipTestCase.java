package models.tests;

import models.Entity;
import models.Relationship;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RelationshipTestCase 
{
	private Entity entity;

	@Test
	public void testCreatingRelationshipSetsRelatedEntityAndCardinality() throws Exception
	{		
		Relationship relationship = new Relationship(this.entity, 1, Double.POSITIVE_INFINITY);
		
		Assert.assertEquals("RelatedEntity", relationship.getRelatedEntity().getName());
		Assert.assertEquals(1.0, relationship.getMinimumCardinality());
		Assert.assertEquals(Double.POSITIVE_INFINITY, relationship.getMaximumCardinality());
	}
	
	@Test(expected=Exception.class)
	public void testMaximumShouldNotBeLowerThanMinimum() throws Exception
	{
		new Relationship(this.entity, 2, 1);
	}
	
	@Before
	public void setUp() throws Exception 
	{
		this.entity = new Entity("RelatedEntity");
	}

	@After
	public void tearDown() throws Exception {
	}
}