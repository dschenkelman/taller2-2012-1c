package infrastructure;

import models.Entity;
import models.IStrongEntity;

import java.util.List;

public interface IProjectContext {
	String getName();
	void setName(String name);
	String getDataDirectory();
    Iterable<Entity> getEntityCollection(Entity entityToExclude);
    Iterable<IStrongEntity> getPossibleStrongEntities(List<IStrongEntity> strongEntitiesToExclude);
}
