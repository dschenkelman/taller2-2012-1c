package controllers.tests.mocks;

import models.Diagram;
import models.Entity;
import models.Relationship;
import controllers.listeners.IDiagramEventListener;

public class MockDiagramListener implements IDiagramEventListener {

	private Diagram diagram;
	private Entity entity;
	private Relationship relationship;
	private String diagramName;
	
	@Override
	public void handleEntityAdded(Diagram diagram, Entity entity) {
		this.diagram = diagram;
		this.entity = entity;
	}
	
	@Override
	public void handleRelationshipAdded(Diagram diagram, Relationship relationship) {
		this.diagram = diagram;
		this.relationship = relationship;
	}
	
	public Diagram getDiagram(){
		return this.diagram;
	}
	
	public Entity getEntity(){
		return this.entity;
	}

	public Relationship getRelationship() {
		return this.relationship;
	}

	@Override
	public void handleSubDiagramCreated(Diagram diagram, String diagramName) {
		this.diagram = diagram;
		this.diagramName = diagramName;
	}

	public String getDiagramName() {
		return this.diagramName;
	}
}
