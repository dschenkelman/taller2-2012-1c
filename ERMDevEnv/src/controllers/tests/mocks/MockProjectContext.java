package controllers.tests.mocks;

import controllers.IProjectContext;
import models.Attribute;
import models.Entity;
import models.EntityCollection;

public class MockProjectContext implements IProjectContext {

    private Iterable<Entity> entityCollection;
    private Iterable<Attribute> attributes;

    @Override
    public Iterable<Entity> getEntityCollection() {
        return this.entityCollection;
    }

    @Override
    public Iterable<Attribute> getPossibleAttributes() {
        return this.attributes;
    }

    public void setEntityCollection(Iterable<Entity> entityCollection){
        this.entityCollection = entityCollection;
    }

    public void setPossibleAttributes(Iterable<Attribute> attributeIterable){
        this.attributes = attributeIterable;
    }
}
