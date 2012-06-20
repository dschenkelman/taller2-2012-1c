package infrastructure;

import models.Diagram;
import models.Entity;
import models.Hierarchy;
import models.IStrongEntity;

import java.util.List;

public interface IProjectContext {
	String getName();
	void setName(String name);
	String getDataDirectory();
    Iterable<IStrongEntity> getPossibleStrongEntities(List<IStrongEntity> strongEntitiesToExclude);
	void clear();
	Iterable<Entity> getAllEntities();
	Iterable<Entity> getContextEntities(Entity entityToExclude);
	Iterable<Hierarchy> getAllHierarchies();
	Iterable<Hierarchy> getContextHierarchies();
	void addContextDiagram(Diagram diagram);
	void addProjectDiagram(Diagram diagram);
}
