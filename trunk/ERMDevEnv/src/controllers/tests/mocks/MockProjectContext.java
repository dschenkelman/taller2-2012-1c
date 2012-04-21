package controllers.tests.mocks;

import controllers.IProjectContext;
import models.EntityCollection;

public class MockProjectContext implements IProjectContext {

    private EntityCollection entityCollection;

    @Override
    public EntityCollection getEntityCollection() {
        return this.entityCollection;
    }

    public void setEntityCollection(EntityCollection entityCollection){
        this.entityCollection = entityCollection;
    }
}
