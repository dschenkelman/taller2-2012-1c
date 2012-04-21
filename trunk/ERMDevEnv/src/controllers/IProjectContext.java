package controllers;

import models.Entity;

public interface IProjectContext {
    public Iterable<Entity> getEntityCollection();
}
