package controllers.tests;

import static org.junit.Assert.*;

import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import models.AttributeCollection;
import models.Cardinality;
import models.Entity;
import models.EntityCollection;
import models.Relationship;
import models.RelationshipEntity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import views.mock.MockRelationshipView;

import controllers.AttributeController;
import controllers.IRelationshipController;
import controllers.IRelationshipEntityController;
import controllers.RelationshipController;
import controllers.factories.mock.MockRelationshipEntityControllerFactory;
import controllers.tests.mocks.MockAttributeController;
import controllers.tests.mocks.MockAttributeControllerFactory;
import controllers.tests.mocks.MockProjectContext;
import controllers.tests.mocks.MockRelationshipEntityController;
import controllers.tests.mocks.MockStrongEntityController;
import controllers.tests.mocks.MockStrongEntityControllerFactory;

public class RelationshipControllerTest {

	private MockAttributeController attController;
	private IRelationshipController relController;
	private EntityCollection entCollection;
	private MockRelationshipView view;
	private List<Relationship> relationships;
	private MockProjectContext pContext;
	private MockAttributeControllerFactory mockAttributeControllerFactory;
	private MockStrongEntityControllerFactory mockStrongEntityControllerFactory;
	private MockStrongEntityController strongEntityController;
	private MockRelationshipEntityControllerFactory mockRelationshipEntityControllerFactory;
	private IRelationshipEntityController mockRelationshipEntityController;
	private List<RelationshipEntity> relEnt;

	@Before
	public void setUp() throws Exception {
		entCollection = new EntityCollection();
		
		Entity entity1 = new Entity("Entity1");
		Entity entity2 = new Entity("Entity2");
		Entity entity3 = new Entity("Entity3");
		Entity entity4 = new Entity("Entity4");

		AttributeCollection attCol = entity1.getAttributes();
		attCol.addAttribute("Attribute1" );
		
		attCol = entity2.getAttributes();
		attCol.addAttribute("Attribute1" );
		attCol.addAttribute("Attribute2" );
		
		attCol = entity3.getAttributes();
		attCol.addAttribute("Attribute1" );
		attCol.addAttribute("Attribute2" );
		attCol.addAttribute("Attribute3" );
		
		attCol = entity4.getAttributes();
		attCol.addAttribute("Attribute1" );
		attCol.addAttribute("Attribute2" );
		attCol.addAttribute("Attribute3" );
		attCol.addAttribute("Attribute4" );
		
		entCollection.add(entity1);
		entCollection.add(entity2);
		entCollection.add(entity3);
		entCollection.add(entity4);
		relationships = new ArrayList<Relationship> ();
		// Set up the views
		
		// Controllers
		relEnt = new ArrayList<RelationshipEntity>();
		attController = new MockAttributeController();
		strongEntityController = new MockStrongEntityController();
		pContext = new MockProjectContext();
		mockRelationshipEntityController = new MockRelationshipEntityController(
				pContext, relEnt);
		
		
		//Initialize parameters
		// Create the Factorories
		mockAttributeControllerFactory = new MockAttributeControllerFactory();
		mockStrongEntityControllerFactory = new MockStrongEntityControllerFactory();
		mockRelationshipEntityControllerFactory = new MockRelationshipEntityControllerFactory();
	
		// Set up the factories
		mockAttributeControllerFactory	.setAttributeController(attController);
		mockStrongEntityControllerFactory.setStrongEntityController(strongEntityController);
		mockRelationshipEntityControllerFactory.setRelationshipEntityController(mockRelationshipEntityController);
		
		view = new MockRelationshipView();
		
		relController = new RelationshipController(pContext, new Relationship(
				UUID.randomUUID(),null, false), view,
				mockAttributeControllerFactory,
				mockStrongEntityControllerFactory,
				mockRelationshipEntityControllerFactory);
		
	

	}



	@Test
	@Ignore
	public void TestCreateRelationshipController() {
						
				
		relController = new RelationshipController(pContext, new Relationship(
				UUID.randomUUID(), null, false), view,
				mockAttributeControllerFactory,
				mockStrongEntityControllerFactory,
				mockRelationshipEntityControllerFactory);
		
		//Test to create the controller
		relController.create();
		assertTrue(view.getController() == relController);
		assertTrue(view.visible == true);
		assertTrue(IterableExtensions.count(relController.getAttributes()) == 0);
		assertTrue(0 == relController.getRelationshipEntities().size() );
		assertTrue(0 == relController.getStrongEntities().count());
	}

	
	@Test
	@Ignore
	public void TestAddRelationshipEntity() {
							
		relController.create();
		List<RelationshipEntity> relEntities = relController.getRelationshipEntities();
		assertTrue(0 == relEntities.size());

		relController.addRelationshipEntity(new RelationshipEntity(UUID
				.randomUUID(), null, null));
		assertTrue(1 == relEntities.size());

		relController.addRelationshipEntity(UUID.randomUUID(), null, null);
		assertTrue(2 == relEntities.size());

	}

		
	@Test(expected = Exception.class)
	@Ignore
	public void TestAddRelationshipWithLessThanTwoRelationshipEntities() throws Exception {
		
		relController.create();
		relController.setName("Relationship1");
		relController.addRelationship();
		
	}

	@Test(expected = Exception.class)
	@Ignore
	public void TestAddRelationshipWithRepeatedName() {
		try {
			relController.create();
			relController.setName("Relationship");
			
			assertTrue (relEnt.size() == 0);
			relationships.add(new Relationship(UUID.randomUUID(),"Relationship",false));
			mockRelationshipEntityController.add(UUID.randomUUID(), null, null);
			mockRelationshipEntityController.add(UUID.randomUUID(), null, null);
		} catch (Exception e1) {
			fail();
		}
			
		//throws exception because there are 2 Relationships with the same name	
		relController.addRelationship();
	}

	@Test
	@Ignore
	public void TestCreateBinaryRelationship() {
		try {
			relController.create();
			relController.setName("Relationship");
			
			assertTrue (relEnt.size() == 0);
			mockRelationshipEntityController.add(UUID.randomUUID(), null, null);
			mockRelationshipEntityController.add(UUID.randomUUID(), null, null);
		} catch (Exception e1) {
			fail();
		}
			
		
		relController.addRelationship();
		assertTrue (relationships.size() == 1);
		assertTrue (relController.getType() == 2);
	}

	@Test
	@Ignore
	public void TestCreateRelationshipWithRoles() {
		fail();
	}

	@Test
	@Ignore
	public void TestValidateToCreateRelationshipWithStrongEntities() {
		/**
		 * Test: 
		 * 1- Validar que sea binaria 
		 * 2- validar que se cumpla la cardinalidad 
		 * 3- Validar que se bloquee el checkbox cuando no se puede crear
		 * */
		fail();
	}

	@Test
	@Ignore
	public void TestCreateRelationshipWithAttributes() {
		fail();
	}
	
}
