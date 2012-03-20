package models.tests;

import models.Entity;
import models.Relationship;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RelationshipTestCase 
{
	private Entity firstEntity;
	private Entity secondEntity;

	@Test
	public void testCreatingRelationshipSetsRelatedEntitiesAndDefaultCardinalities() throws Exception
	{		
		Relationship relationship = new Relationship(this.firstEntity, this.secondEntity);
		
		Assert.assertEquals(1.0, relationship.getFirstCardinality().getMinimum());
		Assert.assertEquals(1.0, relationship.getFirstCardinality().getMaximum());
		Assert.assertEquals(1.0, relationship.getSecondCardinality().getMinimum());
		Assert.assertEquals(1.0, relationship.getSecondCardinality().getMaximum());
		
		Assert.assertEquals("RelatedEntity1", relationship.getFirstEntity().getName());
		Assert.assertEquals("RelatedEntity2", relationship.getSecondEntity().getName());
	}
	
	@Before
	public void setUp() throws Exception 
	{
		this.firstEntity = new Entity("RelatedEntity1");
		this.secondEntity = new Entity("RelatedEntity2");
	}

	@After
	public void tearDown() throws Exception {
	}
}