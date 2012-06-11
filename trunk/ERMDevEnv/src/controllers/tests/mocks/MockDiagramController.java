package controllers.tests.mocks;

import java.awt.Point;

import javax.xml.parsers.ParserConfigurationException;

import models.Entity;
import models.Hierarchy;
import models.Relationship;
import views.IDiagramView;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import controllers.IDiagramController;

public class MockDiagramController implements IDiagramController {

	@Override
	public void addEntity(double x, double y) throws Exception {
	}

	@Override
	public void createEntity() {
	}

	@Override
	public void createHierarchy() {
	}

	@Override
	public void createRelationship() {
	}

	@Override
	public mxCell getAttributeCell(String id) {
		return null;
	}

	@Override
	public mxCell getAttributeConnectorCell(String id) {
		return null;
	}

	@Override
	public mxCell getEntityCell(String id) {
		return null;
	}

	@Override
	public mxGraph getGraph() {
		return null;
	}

	@Override
	public IDiagramView getView() {
		return null;
	}

	@Override
	public void handleDragStart(Point start) {
		
	}

	@Override
	public void handleDrop(Point end) {
		
	}

	@Override
	public boolean hasPendingEntity() {
		return false;
	}

	@Override
	public void save() throws ParserConfigurationException {
		
	}

	@Override
	public void handleCreatedEvent(Entity entity) {
		
	}

	@Override
	public void handleCreatedEvent(Relationship relationship) {
		
	}

	@Override
	public void handleCreatedEvent(Hierarchy hierarchy) {
		
	}
}
