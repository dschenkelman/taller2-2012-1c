package controllers.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import infrastructure.IProjectContext;

import models.Cardinality;
import models.Entity;
import models.EntityCollection;
import models.ModelCollection;
import models.RelationshipEntity;

import org.junit.Before;
import org.junit.Test;

import views.IRelationshipEntityView;
import views.mock.MockRelationshipEntityView;

import controllers.IRelationshipEntityController;
import controllers.RelationshipEntityController;

public class RelationshipEntityTest {

	
	private IProjectContext projectContext;
	private IRelationshipEntityController controller;
	private IRelationshipEntityView mockRelationshipEntityView;
	
	@Before
	public void setUp () {
		mockRelationshipEntityView  = new MockRelationshipEntityView();
		controller = new RelationshipEntityController(projectContext,new ArrayList<RelationshipEntity> (),mockRelationshipEntityView);
	}
	
	@Test
	public void TestCreateRelationshipEntity () {
		controller.create();
		assertEquals(mockRelationshipEntityView.getController(),controller);
		assertTrue(0 == controller.getRelationshipEntities().size());
	}
	
	@Test
	public void TestAddRelationshipEntity() {
		assertEquals(0, controller.getRelationshipEntities().size());
		try {
			String role = "ROLE1";
			Cardinality cardinality = new Cardinality(1,Double.MAX_VALUE);
			UUID entityId = UUID.randomUUID();
			
			controller.add( entityId,  cardinality,  role);
			assertEquals(1, controller.getRelationshipEntities().size());
						
			
			String role2 = "ROLE2";
			Cardinality cardinality2 = new Cardinality(3,5);
			UUID entityId2 = UUID.randomUUID();
			
			controller.add( entityId2,  cardinality2,  role2);
			assertEquals(2, controller.getRelationshipEntities().size());
			
			
		}catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void TestRemoveRelationshipEntity () {
		assertEquals(0, controller.getRelationshipEntities().size());
		try {
			String role = "ROLE1";
			Cardinality cardinality = new Cardinality(1,Double.MAX_VALUE);
			UUID entityId = UUID.randomUUID();
			
			controller.add( entityId,  cardinality,  role);
			assertEquals(1, controller.getRelationshipEntities().size());
			
			
			
			String role2 = "ROLE2";
			Cardinality cardinality2 = new Cardinality(3,5);
			UUID entityId2 = UUID.randomUUID();
			
			controller.add( entityId2,  cardinality2,  role2);
			assertEquals(2, controller.getRelationshipEntities().size());
			
			
			//Test remove method
			controller.remove(entityId);
			assertEquals(1, controller.getRelationshipEntities().size());
			Iterator<RelationshipEntity> ite = controller.getRelationshipEntities().iterator();
			assertEquals(ite.next().getEntityId(),entityId2);
					
		}catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void TestModifyRelationshipEntity() {
		assertEquals(0, controller.getRelationshipEntities().size());
		try {
			String role = "ROLE1";
			Cardinality cardinality = new Cardinality(1,Double.MAX_VALUE);
			UUID entityId = UUID.randomUUID();
			
			controller.add( entityId,  cardinality,  role);
			assertEquals(1, controller.getRelationshipEntities().size());
			
			
			//Test remove method
			String role3 = "ROLE-MODIFIED";
			Cardinality cardinality3 = new Cardinality(5,5);
			controller.modify(entityId, cardinality3, role3);
			
			Iterator<RelationshipEntity> ite = controller.getRelationshipEntities().iterator();
			RelationshipEntity rel = ite.next();
			assertEquals(rel.getEntityId(),entityId);
			assertEquals(rel.getRole(),role3);
			assertEquals(rel.getCardinality(), cardinality3);
			
		}catch (Exception e) {
			fail();
		}
	}
}
