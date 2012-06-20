package infrastructure;

import models.Diagram;
import models.Entity;
import models.Hierarchy;
import models.IStrongEntity;

import java.util.List;
import java.util.UUID;

public interface IProjectContext {
	String getName();
	void setName(String name);
	String getDataDirectory();
//    Iterable<IStrongEntity> getPossibleStrongEntities(List<IStrongEntity> strongEntitiesToExclude);
	void clear();
	Iterable<Entity> getAllEntities(Entity entityToExclude);
	Iterable<Entity> getContextEntities(Entity entityToExclude);
	Iterable<Hierarchy> getAllHierarchies();
	Iterable<Hierarchy> getContextHierarchies();
	void addContextDiagram(Diagram diagram);
	void addProjectDiagram(Diagram diagram);
	Entity getEntity(UUID id);
	Hierarchy getHierarchy(UUID id);
}
