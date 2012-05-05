package controllers;

import models.Entity;
import models.INameable;
import models.IStrongEntity;

import java.util.List;

public interface IProjectContext {
    public Iterable<Entity> getEntityCollection(Entity entityToExclude);

    public Iterable<IStrongEntity> getPossibleStrongEntities(List<IStrongEntity> strongEntitiesToExclude);
}
