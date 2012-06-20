package controllers.tests.mocks;

import models.*;

import infrastructure.IProjectContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockProjectContext implements IProjectContext {

	private static String SubFolder = "Datos";
    private Iterable<Entity> entityCollection;
    private Iterable<INameable> attributes;
    private List<Relationship> relationships;
    
    private List<Diagram> contextDiagrams;
    private List<Diagram> globalDiagrams;
    
	private String name;
	
	public MockProjectContext(){
		this.contextDiagrams = new ArrayList<Diagram>();
		this.globalDiagrams = new ArrayList<Diagram>();
	}

    @Override
    public Iterable<Entity> getAllEntities(Entity entityToExclude) {
        return this.entityCollection;
    }

    public void setEntityCollection(Iterable<Entity> entityCollection){
        this.entityCollection = entityCollection;
    }

    public void setPossibleAttributes(Iterable<INameable> attributeIterable){
        this.attributes = attributeIterable;
    }

	public void setRelationshipCollection(List<Relationship> relationships) {
		this.relationships= relationships;
	}

	public List<Relationship> getRelationshipCollection() {
		return relationships;
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

	@Override
	public void addContextDiagram(Diagram diagram) {
		this.contextDiagrams.add(diagram);
	}

	@Override
	public void addProjectDiagram(Diagram diagram) {
		this.globalDiagrams.add(diagram);
	}

	@Override
	public void clear() {		
	}

	@Override
	public Iterable<Hierarchy> getAllHierarchies() {
		return null;
	}

	@Override
	public Iterable<Entity> getContextEntities(Entity entityToExclude) {
		return null;
	}

	@Override
	public Iterable<Hierarchy> getContextHierarchies() {
		return null;
	}

	@Override
	public Entity getEntity(UUID id) {
		return null;
	}

	@Override
	public Hierarchy getHierarchy(UUID id) {
		return null;
	}
	
	public List<Diagram> getContextDiagrams(){
		return this.contextDiagrams;
	}
	
	public List<Diagram> getGlobalDiagrams(){
		return this.globalDiagrams;
	}
}