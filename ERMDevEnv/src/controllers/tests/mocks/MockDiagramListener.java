package controllers.tests.mocks;

import models.Diagram;
import models.Entity;
import controllers.listeners.IDiagramEventListener;

public class MockDiagramListener implements IDiagramEventListener {

	private Diagram diagram;
	private Entity entity;
	
	@Override
	public void handleEntityAdded(Diagram diagram, Entity entity) {
		this.diagram = diagram;
		this.entity = entity;
	}
	
	public Diagram getDiagram(){
		return this.diagram;
	}
	
	public Entity getEntity(){
		return this.entity;
	}
}
