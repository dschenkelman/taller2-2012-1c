package controllers.tests.mocks;

import controllers.IProjectContext;
import models.Attribute;
import models.Entity;
import models.EntityCollection;
import models.INameable;

public class MockProjectContext implements IProjectContext {

    private Iterable<Entity> entityCollection;
    private Iterable<INameable> attributes;

    @Override
    public Iterable<Entity> getEntityCollection() {
        return this.entityCollection;
    }

    @Override
    public Iterable<INameable> getPossibleAttributes() {
        return this.attributes;
    }

    public void setEntityCollection(Iterable<Entity> entityCollection){
        this.entityCollection = entityCollection;
    }

    public void setPossibleAttributes(Iterable<INameable> attributeIterable){
        this.attributes = attributeIterable;
    }
}
