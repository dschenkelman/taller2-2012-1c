package controllers.tests.mocks;

import models.*;

import infrastructure.IProjectContext;

import java.util.List;

public class MockProjectContext implements IProjectContext {

	private static String SubFolder = "Datos";
    private Iterable<Entity> entityCollection;
    private Iterable<INameable> attributes;
	private String name;

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

	public void setRelationshipCollection(List<Relationship> relationships) {
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getDataDirectory() {
		if (this.name != null){
			return this.name + "/" + SubFolder;
		}
		
		return null;
	}
}
