package infrastructure;

import models.Diagram;
import models.Entity;
import models.Hierarchy;
import java.util.UUID;

public interface IProjectContext {
	String getName();
	void setName(String name);
	String getDataDirectory();
	void clear();
	Iterable<Entity> getAllEntities(Entity entityToExclude);
	Iterable<Entity> getContextEntities(Entity entityToExclude);
	Iterable<Entity> getContextEntities();
	Iterable<Hierarchy> getAllHierarchies();
	Iterable<Hierarchy> getContextHierarchies();
	void addContextDiagram(Diagram diagram);
	void addProjectDiagram(Diagram diagram);
	Hierarchy getHierarchy(UUID id);
	Entity getEntity(UUID entityId);
	Diagram getContextDiagram(String defaultDiagramName);
}
