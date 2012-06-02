package controllers.tests;


import javax.xml.parsers.ParserConfigurationException;

import infrastructure.Func;
import infrastructure.IterableExtensions;
import models.Cardinality;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.tests.TestUtilities;

import com.mxgraph.model.mxCell;

import controllers.DiagramController;
import controllers.tests.mocks.MockDiagramView;
import controllers.tests.mocks.MockDiagramXmlManager;
import controllers.tests.mocks.MockEntityController;
import controllers.tests.mocks.MockEntityControllerFactory;
import controllers.tests.mocks.MockProjectContext;
import controllers.tests.mocks.MockRelationshipController;
import controllers.tests.mocks.MockRelationshipControllerFactory;
import controllers.tests.mocks.MockXmlFileManager;

public class DiagramControllerTestCase {

	private MockProjectContext projectContext;
	private MockDiagramView diagramView;
	private MockEntityController entityController;
	private MockEntityControllerFactory entityControllerFactory;
	private MockRelationshipControllerFactory relationshipControllerFactory;
	private MockRelationshipController relationshipController;
	private MockXmlFileManager xmlFileManager;
	private MockDiagramXmlManager diagramXmlManager;

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
		this.xmlFileManager = new MockXmlFileManager();
		this.diagramXmlManager = new MockDiagramXmlManager();
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
		
		Assert.assertSame(entity, IterableExtensions.firstOrDefault
				(diagramController.getDiagram().getEntities(), new Func<Entity, String, Boolean>() {

					@Override
					public Boolean execute(Entity entity, String name) {
						return entity.getName().equalsIgnoreCase(name);
					}
				}, "Product"));
		
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
	public void testShouldCreateCellsForRelationshipWhenAddingRelationship() throws Exception{

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
		
		relationship.getAttributes().addAttribute("Attribute1");
		relationship.getAttributes().addAttribute("Attribute2");
		
		diagramController.handleCreatedEvent(relationship);
		
		Func<Entity, String, Boolean> cmpFunc = new Func<Entity, String, Boolean>() {
			@Override
			public Boolean execute(Entity entity, String name) {
				return entity.getName().equalsIgnoreCase(name);
			};
		};
		
		Assert.assertSame(entity1, IterableExtensions.firstOrDefault
				(diagramController.getDiagram().getEntities(), cmpFunc, "Entity1"));
		
		Assert.assertSame(entity2, IterableExtensions.firstOrDefault
				(diagramController.getDiagram().getEntities(), cmpFunc, "Entity2"));
		
		Assert.assertSame(entity3, IterableExtensions.firstOrDefault
				(diagramController.getDiagram().getEntities(), cmpFunc, "Entity3"));
		
		Assert.assertSame(relationship, IterableExtensions.firstOrDefault
				(diagramController.getDiagram().getRelationships(), new Func<Relationship, String, Boolean>() {
					@Override
					public Boolean execute(Relationship relationship, String name) {
						return relationship.getName().equalsIgnoreCase(name);
					};}, "Relationship"));
		
		mxCell relationshipCell = diagramController.getRelationshipCell(relationship.getId().toString());
		
		Assert.assertEquals("Relationship", diagramController.getGraph().getLabel(relationshipCell));
		Assert.assertTrue(diagramController.getGraph().getModel().isVertex(relationshipCell));
		Assert.assertEquals(40.0, relationshipCell.getGeometry().getX(), 0);
		Assert.assertEquals(65.0, relationshipCell.getGeometry().getY(), 0);
		
		mxCell entity1Cell = diagramController.getEntityCell(entity1.getId().toString());
		mxCell relationshipEntity11Cell = 
			diagramController.getRelationshipConnectorCell
				(relationship.getId().toString()+entity1.getId().toString()+"Role1");
		mxCell relationshipEntity12Cell = 
			diagramController.getRelationshipConnectorCell
				(relationship.getId().toString()+entity1.getId().toString()+"Role2");
		Assert.assertTrue(diagramController.getGraph().getModel().isEdge(relationshipEntity11Cell));
		Object[] entity1RelationshipConnectors = diagramController.getGraph().getEdgesBetween(entity1Cell, relationshipCell);
		Assert.assertEquals(2, entity1RelationshipConnectors.length);
		Assert.assertSame(relationshipEntity11Cell, entity1RelationshipConnectors[0]);
		Assert.assertSame(relationshipEntity12Cell, entity1RelationshipConnectors[1]);
		Assert.assertEquals("Role1 (0,1)", relationshipEntity11Cell.getValue());
		Assert.assertEquals("Role2 (1,1)", relationshipEntity12Cell.getValue());
		
		mxCell entity2Cell = diagramController.getEntityCell(entity2.getId().toString());
		mxCell relationshipEntity2Cell = 
			diagramController.getRelationshipConnectorCell
				(relationship.getId().toString()+entity2.getId().toString()+"");
		Assert.assertTrue(diagramController.getGraph().getModel().isEdge(relationshipEntity2Cell));
		Object[] entity2RelationshipConnectors = diagramController.getGraph().getEdgesBetween(entity2Cell, relationshipCell);
		Assert.assertEquals(1, entity2RelationshipConnectors.length);
		Assert.assertSame(relationshipEntity2Cell, entity2RelationshipConnectors[0]);
		Assert.assertEquals("(0,*)", relationshipEntity2Cell.getValue());
		
		mxCell entity3Cell = diagramController.getEntityCell(entity3.getId().toString());
		mxCell relationshipEntity3Cell = 
			diagramController.getRelationshipConnectorCell
				(relationship.getId().toString()+entity3.getId().toString()+"");
		Assert.assertTrue(diagramController.getGraph().getModel().isEdge(relationshipEntity3Cell));
		Object[] entity3RelationshipConnectors = diagramController.getGraph().getEdgesBetween(entity3Cell, relationshipCell);
		Assert.assertEquals(1, entity3RelationshipConnectors.length);
		Assert.assertSame(relationshipEntity3Cell, entity3RelationshipConnectors[0]);
		Assert.assertEquals("(*,*)", relationshipEntity3Cell.getValue());
		
		mxCell attribute1Cell = diagramController.getAttributeCell(relationship.getId().toString()+"Attribute1");
		Assert.assertTrue(diagramController.getGraph().getModel().isVertex(attribute1Cell));
		Assert.assertEquals("Attribute1", attribute1Cell.getValue());
		
		mxCell relationshipAttribute1Cell = diagramController.getAttributeConnectorCell(relationship.getId().toString()+"Attribute1"+"AttributeConnector");
		Assert.assertTrue(diagramController.getGraph().getModel().isEdge(relationshipAttribute1Cell));
		
		Object[] attribute1Connectors = diagramController.getGraph().getEdgesBetween(attribute1Cell, relationshipCell);
		Assert.assertEquals(1, attribute1Connectors.length);
		Assert.assertSame(relationshipAttribute1Cell, attribute1Connectors[0]);
		
		mxCell attribute2Cell = diagramController.getAttributeCell(relationship.getId().toString()+"Attribute2");
		Assert.assertTrue(diagramController.getGraph().getModel().isVertex(attribute2Cell));
		Assert.assertEquals("Attribute2", attribute2Cell.getValue());
		
		mxCell relationshipAttribute2Cell = diagramController.getAttributeConnectorCell(relationship.getId().toString()+"Attribute2"+"AttributeConnector");
		Assert.assertTrue(diagramController.getGraph().getModel().isEdge(relationshipAttribute2Cell));
		
		Object[] attribute2Connectors = diagramController.getGraph().getEdgesBetween(attribute2Cell, relationshipCell);
		Assert.assertEquals(1, attribute2Connectors.length);
		Assert.assertSame(relationshipAttribute2Cell, attribute2Connectors[0]);
	}

	@Test
	public void testShouldCallSaveWithDiagramNameDashCompWhenSaveIsCalled() throws ParserConfigurationException{
		Document document = TestUtilities.createDocument();
		this.xmlFileManager.setDocumentToCreate(document);
		this.diagramXmlManager.setElementNameOfRoot("diagram");
		
		DiagramController controller = this.createController();
		controller.getDiagram().setName("Diagram");
		
		Assert.assertNull(this.xmlFileManager.getDocumentToSave());
		Assert.assertNull(this.xmlFileManager.getPathToSave());
		Assert.assertFalse(this.xmlFileManager.wasCreateDocumentCalled());
		
		controller.save();
		
		Assert.assertTrue(this.xmlFileManager.wasCreateDocumentCalled());
		Assert.assertNotNull(this.xmlFileManager.getDocumentToSave());
		Assert.assertNotNull(this.xmlFileManager.getPathToSave());
		
		Assert.assertSame(controller.getDiagram(), this.diagramXmlManager.getDiagramRelatedToElement());
		Assert.assertSame(document, this.xmlFileManager.getDocumentToSave());
		Assert.assertEquals("diagram", ((Element)document.getFirstChild()).getTagName());
		Assert.assertEquals("Diagram-comp", this.xmlFileManager.getPathToSave());
	}
	
	private void addEntityToDiagram(DiagramController diagramController, 
			Entity entity, double x, double y) throws Exception {
		diagramController.createEntity();
		diagramController.handleCreatedEvent(entity);
		diagramController.addEntity(x, y);
	}
	
	private DiagramController createController() {
		return new DiagramController(this.projectContext, this.diagramView,
				this.entityControllerFactory, this.relationshipControllerFactory, this.xmlFileManager, this.diagramXmlManager);
	}

}
