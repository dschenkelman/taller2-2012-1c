package controllers.tests.mocks;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import javax.xml.parsers.ParserConfigurationException;

import models.Diagram;
import models.Entity;
import models.Hierarchy;
import models.Relationship;
import views.IDiagramView;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import controllers.IDiagramController;
import controllers.listeners.IDiagramEventListener;

public class MockDiagramController implements IDiagramController {

	private Diagram diagram;
	
	private MockDiagramView view;

	private List<IDiagramEventListener> listeners;
	
	public MockDiagramController(){
		this.diagram = new Diagram();
		this.view = new MockDiagramView();
		this.listeners = new ArrayList<IDiagramEventListener>();
	}
	
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
		return this.view;
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

	@Override
	public Diagram getDiagram() {
		return this.diagram;
	}

	@Override
	public void addListener(IDiagramEventListener listener) {
		this.listeners.add(listener);
		
	}

	public List<IDiagramEventListener> getListeners() {
		return this.listeners;
	}

	@Override
	public void createSubDiagram(String diagramName) {
	}
	
	public void setDiagram(Diagram diagram){
		this.diagram = diagram;
	}

	@Override
	public void load(Diagram diagram) {
		// TODO Auto-generated method stub
		
	}
}
