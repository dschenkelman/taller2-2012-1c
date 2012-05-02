package controllers.tests;


import models.Cardinality;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mxgraph.model.mxCell;

import controllers.DiagramController;
import controllers.tests.mocks.MockDiagramView;
import controllers.tests.mocks.MockEntityController;
import controllers.tests.mocks.MockEntityControllerFactory;
import controllers.tests.mocks.MockProjectContext;
import controllers.tests.mocks.MockRelationshipController;
import controllers.tests.mocks.MockRelationshipControllerFactory;

public class DiagramControllerTestCase {

	private MockProjectContext projectContext;
	private MockDiagramView diagramView;
	private MockEntityController entityController;
	private MockEntityControllerFactory entityControllerFactory;
	private MockRelationshipControllerFactory relationshipControllerFactory;
	private MockRelationshipController relationshipController;

	@Before
	public void setUp() throws Exception {
		this.projectContext = new MockProjectContext();
		this.diagramView = new MockDiagramView();
		this.entityController = new MockEntityController();
		this.entityControllerFactory = new MockEntityControllerFactory();
		this.entityControllerFactory.setController(this.entityController);
		this.relationshipController = new MockRelationshipController();
		this.relationshipControllerFactory = new MockRelationshipControllerFactory();
		this.relationshipControllerFactory.setController(this.relationshipController);
	}
	
	@Test
	public void testShouldSetControllerToView()
	{
		DiagramController diagramController = this.createController();
		
		Assert.assertSame(diagramController, this.diagramView.getController());
		Assert.assertSame(diagramController.getGraph(), this.diagramView.getGraph());
	}
	
	@Test
	public void testShouldCreateEntityThroughEntityControllerWhenCreatingEntityWithoutPosition(){
		Entity entity = new Entity("Product");
				
		DiagramController diagramController = this.createController();
		
		Assert.assertEquals(0, this.entityController.getCreateCallsCount());
		Assert.assertFalse(diagramController.hasPendingEntity());
		
		diagramController.createEntity();
		diagramController.handleCreatedEvent(entity);
		
		Assert.assertEquals(1, this.entityController.getCreateCallsCount());
		Assert.assertTrue(diagramController.hasPendingEntity());
	}
	
	@Test
	public void testShouldNotCreateEntityIfThereIsAPendingEntity(){
		Entity entity = new Entity("Product");
		
		DiagramController diagramController = this.createController();
		
		Assert.assertEquals(0, this.entityController.getCreateCallsCount());
		Assert.assertFalse(diagramController.hasPendingEntity());
		
		diagramController.createEntity();
		diagramController.handleCreatedEvent(entity);
		
		Assert.assertEquals(1, this.entityController.getCreateCallsCount());
		Assert.assertTrue(diagramController.hasPendingEntity());
		
		diagramController.createEntity();
		
		Assert.assertEquals(1, this.entityController.getCreateCallsCount());
		Assert.assertTrue(diagramController.hasPendingEntity());
	}
	
	@Test
	public void testShouldCreateCellsForEntityAttributesAndLinksWhenAddingEntity() throws Exception

	{
		Entity entity = new Entity("Product");
		entity.getAttributes().addAttribute("Stock");
		entity.getAttributes().addAttribute("Name");
		entity.getAttributes().addAttribute("Price");
				
		DiagramController diagramController = this.createController();
		
		diagramController.createEntity();
		diagramController.handleCreatedEvent(entity);
		
		Assert.assertTrue(diagramController.hasPendingEntity());
		
		diagramController.addEntity(20, 30);
		
		Assert.assertFalse(diagramController.hasPendingEntity());
		
		mxCell entityCell = diagramController.getEntityCell(entity.getId().toString());
		Assert.assertEquals("Product", diagramController.getGraph().getLabel(entityCell));
		Assert.assertTrue(diagramController.getGraph().getModel().isVertex(entityCell));
		Assert.assertEquals(20.0, entityCell.getGeometry().getX(), 0);
		Assert.assertEquals(30.0, entityCell.getGeometry().getY(), 0);
		
		mxCell stockCell = diagramController.getAttributeCell(entity.getId().toString()+"Stock");
		Assert.assertEquals("Stock", diagramController.getGraph().getLabel(stockCell));
		Assert.assertTrue(diagramController.getGraph().getModel().isVertex(stockCell));
		
		mxCell nameCell = diagramController.getAttributeCell(entity.getId().toString()+"Name");
		Assert.assertEquals("Name", diagramController.getGraph().getLabel(nameCell));
		Assert.assertTrue(diagramController.getGraph().getModel().isVertex(nameCell));
		
		mxCell priceCell = diagramController.getAttributeCell(entity.getId().toString()+"Price");
		Assert.assertEquals("Price", diagramController.getGraph().getLabel(priceCell));
		Assert.assertTrue(diagramController.getGraph().getModel().isVertex(priceCell));
		
		mxCell entityStockCell = diagramController.getAttributeConnectorCell(entity.getId().toString()+"StockAttributeConnector");
		Assert.assertTrue(diagramController.getGraph().getModel().isEdge(entityStockCell));
		Object[] entityStockConnectors = diagramController.getGraph().getEdgesBetween(entityCell, stockCell);
		Assert.assertEquals(1, entityStockConnectors.length);
		Assert.assertSame(entityStockCell, entityStockConnectors[0]);
		
		mxCell entityNameCell = diagramController.getAttributeConnectorCell(entity.getId().toString()+"NameAttributeConnector");
		Assert.assertTrue(diagramController.getGraph().getModel().isEdge(entityNameCell));
		Object[] entityNameConnectors = diagramController.getGraph().getEdgesBetween(entityCell, nameCell);
		Assert.assertEquals(1, entityNameConnectors.length);
		Assert.assertSame(entityNameCell, entityNameConnectors[0]);
		
		mxCell entityPriceCell = diagramController.getAttributeConnectorCell(entity.getId().toString()+"PriceAttributeConnector");
		Assert.assertTrue(diagramController.getGraph().getModel().isEdge(entityPriceCell));
		Object[] entityPriceConnectors = diagramController.getGraph().getEdgesBetween(entityCell, priceCell);
		Assert.assertEquals(1, entityPriceConnectors.length);
		Assert.assertSame(entityPriceCell, entityPriceConnectors[0]);
	}

	@Test
	public void testShouldCreateRelationshipThroughRelationshipControllerWhenCreateRelationshipIsCalled(){
		DiagramController controller = this.createController();
		
		Assert.assertFalse(this.relationshipController.createWasCalled());
		
		controller.createRelationship();
		
		Assert.assertTrue(this.relationshipController.createWasCalled());
	}
	
	@Test
	public void testShouldRegisterToHandleRelationshipCreatedEvents(){
		DiagramController controller = this.createController();
		
		Assert.assertEquals(0, this.relationshipController.getListeners().size());
		
		controller.createRelationship();
		
		Assert.assertEquals(1, this.relationshipController.getListeners().size());
		Assert.assertSame(controller, this.relationshipController.getListeners().toArray()[0]);
	}
	
	@Test
	public void testShouldRegisterToHandleEntityCreatedEvents(){
		DiagramController diagramController = this.createController();
		Assert.assertEquals(0, this.entityController.getListeners().size());
		
		diagramController.createEntity();
		
		Assert.assertEquals(1, this.entityController.getListeners().size());
		Assert.assertSame(diagramController, this.entityController.getListeners().toArray()[0]);
	}
		
	@Test
	public void testShouldCreateCellsForRelationshipWhenAddingRelationshipWithoutAttributes() throws Exception{
		Entity entity1 = new Entity("Entity1");
		Entity entity2 = new Entity("Entity2");
		Entity entity3 = new Entity("Entity3");
		
		DiagramController diagramController = this.createController();
		
		this.addEntityToDiagram(diagramController, entity1, 20, 30);
		this.addEntityToDiagram(diagramController, entity2, 60, 30);
		this.addEntityToDiagram(diagramController, entity3, 20, 100);
		
		RelationshipEntity relationshipEntity11 = 
			new RelationshipEntity(entity1, new Cardinality(0, 1), "Role1");
		RelationshipEntity relationshipEntity12 = 
			new RelationshipEntity(entity1, new Cardinality(1, 1), "Role2");
		RelationshipEntity relationshipEntity2 = 
			new RelationshipEntity(entity2, new Cardinality(0, Double.POSITIVE_INFINITY), "");
		RelationshipEntity relationshipEntity3 = 
			new RelationshipEntity(entity3, new Cardinality(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), "");
		
		Relationship relationship = new Relationship(relationshipEntity11, relationshipEntity12);
		relationship.setName("Relationship");
		relationship.addRelationshipEntity(relationshipEntity2);
		relationship.addRelationshipEntity(relationshipEntity3);
		
		diagramController.handleCreatedEvent(relationship);
		
		mxCell relationshipCell = diagramController.getRelationshipCell(relationship.getId().toString());
		
		Assert.assertEquals("Relationship", diagramController.getGraph().getLabel(relationshipCell));
		Assert.assertTrue(diagramController.getGraph().getModel().isVertex(relationshipCell));
		Assert.assertEquals(40.0, relationshipCell.getGeometry().getX(), 0);
		Assert.assertEquals(65.0, relationshipCell.getGeometry().getY(), 0);
	}

	private void addEntityToDiagram(DiagramController diagramController, 
			Entity entity, double x, double y) {
		diagramController.createEntity();
		diagramController.handleCreatedEvent(entity);
		diagramController.addEntity(x, y);
	}
	
	private DiagramController createController() {
		return new DiagramController(this.projectContext, this.diagramView,
				this.entityControllerFactory, this.relationshipControllerFactory);
	}

}
