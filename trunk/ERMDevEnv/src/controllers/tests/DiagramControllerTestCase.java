package controllers.tests;


import models.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mxgraph.model.mxCell;

import controllers.DiagramController;
import controllers.tests.mocks.MockDiagramView;
import controllers.tests.mocks.MockEntityController;
import controllers.tests.mocks.MockEntityControllerFactory;
import controllers.tests.mocks.MockProjectContext;

public class DiagramControllerTestCase {

	private MockProjectContext projectContext;
	private MockDiagramView diagramView;
	private MockEntityController entityController;
	private MockEntityControllerFactory entityControllerFactory;

	@Before
	public void setUp() throws Exception {
		this.projectContext = new MockProjectContext();
		this.diagramView = new MockDiagramView();
		this.entityController = new MockEntityController();
		this.entityControllerFactory = new MockEntityControllerFactory();
		this.entityControllerFactory.setController(this.entityController);
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
		
		this.entityController.setEntity(entity);
		
		DiagramController diagramController = this.createController();
		
		Assert.assertEquals(0, this.entityController.getCreateCallsCount());
		Assert.assertFalse(diagramController.hasPendingEntity());
		
		diagramController.createEntity();
		
		Assert.assertEquals(1, this.entityController.getCreateCallsCount());
		Assert.assertTrue(diagramController.hasPendingEntity());
	}
	
	@Test
	public void testShouldNotCreateEntityIfThereIsAPendingEntity(){
		Entity entity = new Entity("Product");
		
		this.entityController.setEntity(entity);
		
		DiagramController diagramController = this.createController();
		
		Assert.assertEquals(0, this.entityController.getCreateCallsCount());
		Assert.assertFalse(diagramController.hasPendingEntity());
		
		diagramController.createEntity();
		
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
		
		this.entityController.setEntity(entity);
		
		DiagramController diagramController = this.createController();
		
		diagramController.createEntity();
		
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

	private DiagramController createController() {
		return new DiagramController(this.projectContext, this.diagramView, this.entityControllerFactory);
	}

}
