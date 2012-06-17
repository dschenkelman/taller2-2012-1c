package controllers.listeners;

import models.Diagram;
import models.Entity;

public interface IDiagramEventListener {
	void handleEntityAdded(Diagram diagram, Entity entity);
}
