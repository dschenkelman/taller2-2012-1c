package controllers;

import models.Entity;
import models.INameable;

public interface IProjectContext {
    public Iterable<Entity> getEntityCollection();

    Iterable<INameable> getPossibleAttributes();
}
