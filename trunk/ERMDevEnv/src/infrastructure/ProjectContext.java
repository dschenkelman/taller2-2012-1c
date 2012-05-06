package infrastructure;

import java.util.List;

import models.Entity;
import models.IStrongEntity;

public class ProjectContext implements IProjectContext {

	@Override
	public Iterable<Entity> getEntityCollection(Entity entityToExclude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<IStrongEntity> getPossibleStrongEntities(
			List<IStrongEntity> strongEntitiesToExclude) {
		// TODO Auto-generated method stub
		return null;
	}

}
