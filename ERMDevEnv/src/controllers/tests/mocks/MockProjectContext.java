package controllers.tests.mocks;

import controllers.IProjectContext;
import models.Entity;
import models.EntityCollection;

public class MockProjectContext implements IProjectContext {

    private Iterable<Entity> entityCollection;

    @Override
    public Iterable<Entity> getEntityCollection() {
        return this.entityCollection;
    }

    public void setEntityCollection(Iterable<Entity> entityCollection){
        this.entityCollection = entityCollection;
    }
}
