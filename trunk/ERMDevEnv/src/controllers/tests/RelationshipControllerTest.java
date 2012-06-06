package controllers.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import models.AttributeCollection;
import models.Entity;
import models.EntityCollection;
import models.Relationship;
import models.RelationshipEntity;

import org.junit.Before;
import org.junit.Test;

import views.mock.MockRelationshipView;

import controllers.AttributeController;
import controllers.IRelationshipController;
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
	private AttributeCollection attColection;
	private EntityCollection entCollection;
	private MockRelationshipView view;
	private MockProjectContext pContext;
	private MockAttributeControllerFactory mockAttributeControllerFactory;
	private MockStrongEntityControllerFactory mockStrongEntityControllerFactory;
	private MockStrongEntityController strongEntityController;
	private MockRelationshipEntityControllerFactory mockRelationshipEntityControllerFactory;
	private MockRelationshipEntityController mockRelationshipEntityController;
	private List<RelationshipEntity>  relEnt ;
	
	@Before
	public void setUp () {
		
		entCollection = new EntityCollection ();
		entCollection.add(new Entity("Entity1"));
		entCollection.add(new Entity("Entity2"));
		entCollection.add(new Entity("Entity3"));
		entCollection.add(new Entity("Entity4"));
		
		attColection = new AttributeCollection();
		try {
			attColection.addAttribute("Attribute1");
			attColection.addAttribute("Attribute2");
			attColection.addAttribute("Attribute3");
			attColection.addAttribute("Attribute4");
			attColection.addAttribute("Attribute5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Controllers
		relEnt = new ArrayList <RelationshipEntity> ();
		attController = new MockAttributeController();
		strongEntityController = new MockStrongEntityController ();
		pContext = new MockProjectContext ();
		mockRelationshipEntityController = new MockRelationshipEntityController(pContext,relEnt);
		
		//Create the Factorories
		mockAttributeControllerFactory = new MockAttributeControllerFactory();
		mockStrongEntityControllerFactory = new MockStrongEntityControllerFactory();
		mockRelationshipEntityControllerFactory = new MockRelationshipEntityControllerFactory();
		
		//Set up the factories
		this.mockAttributeControllerFactory.setAttributeController(attController);
		this.mockStrongEntityControllerFactory.setStrongEntityController(strongEntityController);
		mockRelationshipEntityControllerFactory.setRelationshipEntityController(mockRelationshipEntityController);
		
		// Set up the views
		view = new MockRelationshipView () ;
		
		
		
		relController = new RelationshipController (pContext, view, 
				mockAttributeControllerFactory, mockStrongEntityControllerFactory, 
				mockRelationshipEntityControllerFactory);
		
		
	}
	
	@Test
	public void TestCreateRelationshipController() {
		relController.create();
		assertTrue (view.getController() == relController);
		assertTrue (view.visible == true);
		assertTrue (relController.getAttributes().size() == 5);
		assertTrue (relController.getRelationshipEntities().size() == 3);
	}
	
	
	@Test (expected=Exception.class)
	public void TestAddRelationshipWithLessThanTwoRelationshipEntities () {
		
	}
	
	@Test (expected=Exception.class)
	public void TestAddRelationshipWithRepeatedName() {
		
		
	}
	
	@Test
	public void TestCreateBinaryRelationship() {
		
	}
	
	@Test
	public void TestCreateRelationshipWithRoles() {
		
	}
	
	@Test
	public void TestCreateRelationshipWithStrongEntities(){
		
	}
	
	@Test
	public void TestValidateToCreateRelationshipWithStrongEntities() {
		/**
		 * Test: 
		 * 1- Validar que sea binaria
		 * 2- validar que se cumpla la cardinalidad
		 * 3- Validar que se bloquee el checkbox cuando no se puede crear
		 * */
		
		
	}
	
	@Test
	public void TestCreateRelationshipWithAttributes(){
		
	}
	

	
}
