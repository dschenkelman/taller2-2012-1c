package controllers.tests;

import static org.junit.Assert.*;

import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.Collections;
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
	private MockRelationshipEntityController mockRelationshipEntityController;
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
		pContext.setRelationshipCollection(relationships);
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
	public void TestCreateRelationshipController() {
						
				
		relController = new RelationshipController(pContext, new Relationship(
				UUID.randomUUID(), null, false), view,
				mockAttributeControllerFactory,
				mockStrongEntityControllerFactory,
				mockRelationshipEntityControllerFactory);
		
		//Test to create the controller
		relController.create();
		assertTrue(view.getController() == relController);
	}
	
	@Test
	public void TestAddRelationship() {
		
		//There are no relationships at first in the project context
		assertTrue (this.pContext.getRelationshipCollection().size()==0);
		
		relController.create();
		assertTrue(0 == relEnt.size());
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		
		
		mockRelationshipEntityController.add(uuid1, null, null);
		assertTrue(1 == relEnt.size());

		mockRelationshipEntityController.add(uuid2, null, null);
		assertTrue(2 == relEnt.size());

		try {
			relController.setName("Relationship");
			relController.isComposition(true);
			relController.add();
		}catch (Exception e) {
			e.printStackTrace();
			fail ();
		}
		
		//The relationship should be added to the project context
		assertTrue (this.pContext.getRelationshipCollection().size()==1);
		Relationship aux =  pContext.getRelationshipCollection().iterator().next();
		
		assertEquals (aux.getName(),"Relationship");
		assertTrue (IterableExtensions.count(aux.getRelationshipEntities()) == 2);
		RelationshipEntity relEnt1 = aux.getRelationshipEntities().iterator().next();
		RelationshipEntity relEnt2 = aux.getRelationshipEntities().iterator().next();
		assertTrue (relEnt1.getEntityId().equals(uuid1));
		assertTrue (relEnt2.getEntityId().equals(uuid2));
		
	}

		
	@Test(expected = Exception.class)
	public void TestAddRelationshipWithLessThanTwoRelationshipEntities() throws Exception {
		
		//There are no relationships at first in the project context
		assertTrue (this.pContext.getRelationshipCollection().size()==0);
		
		relController.create();
		try {
			relController.add();
			fail();
		} catch (Exception e) {
			//There shouldn't be any relationship since it cannot be added
			assertTrue (this.pContext.getRelationshipCollection().size()==0);
		}
		
		
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
