package controllers.tests.mocks;

import models.*;

import infrastructure.IProjectContext;

import java.util.List;

public class MockProjectContext implements IProjectContext {

    private Iterable<Entity> entityCollection;
    private Iterable<INameable> attributes;

    @Override
    public Iterable<Entity> getEntityCollection(Entity entityToExclude) {
        return this.entityCollection;
    }

    @Override
    public Iterable<IStrongEntity> getPossibleStrongEntities(List<IStrongEntity> strongEntitiesToExclude) {
        return null;
    }

    public void setEntityCollection(Iterable<Entity> entityCollection){
        this.entityCollection = entityCollection;
    }

    public void setPossibleAttributes(Iterable<INameable> attributeIterable){
        this.attributes = attributeIterable;
    }
}
