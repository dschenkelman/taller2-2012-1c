package controllers.listeners;

import models.Diagram;
import models.Entity;
import models.Relationship;

public interface IDiagramEventListener {
	void handleEntityAdded(Diagram diagram, Entity entity);
	void handleRelationshipAdded(Diagram diagram, Relationship relantionship);
}
