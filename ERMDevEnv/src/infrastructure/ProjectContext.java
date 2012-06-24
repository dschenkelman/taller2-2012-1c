package infrastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import models.Diagram;
import models.Entity;
import models.EntityCollection;
import models.Hierarchy;



public class ProjectContext implements IProjectContext {

	private EntityCollection entityCollection;
	
	private static String SubFolder = "Datos";
	
	private List<Diagram> projectDiagram;
	private List<Diagram> contextDiagram;

	private String name;

    public ProjectContext() {
    	this.projectDiagram = new ArrayList<Diagram>();
    	this.contextDiagram = new ArrayList<Diagram>();
    }

    @Override
    public Iterable<Entity> getAllEntities(Entity entityToExclude) {
    	return this.getEntities(entityToExclude, this.projectDiagram);
    }
    
    // returns all entities from active diagram and all of his parents
    @Override
    public Iterable<Entity> getContextEntities(Entity entityToExclude) {
       return this.getEntities(entityToExclude, this.contextDiagram);
    }
    
    // returns all entities of active diagram
    @Override
	public Iterable<Entity> getContextEntities() {
    	Set<Entity> entities = new HashSet<Entity>();
    	int size = this.contextDiagram.size();
    	for (Entity item : this.contextDiagram.get(size - 1).getEntities())
       		entities.add(item);
    	return entities;
	}
    
    @Override
    public Iterable<Hierarchy> getContextHierarchies() {
    	return this.getHierarchies(this.contextDiagram);
    }
    
    @Override
    public Iterable<Hierarchy> getAllHierarchies() {
    	return this.getHierarchies(this.projectDiagram);
    }
    
    private Iterable<Entity> getEntities(Entity entityToExclude, List<Diagram> diagrams) {
    	Set<Entity> entities = new HashSet<Entity>();
        for (Diagram diagram : diagrams) {
        	if (entityToExclude == null) {
        		for (Entity item : diagram.getEntities())
           			entities.add(item);
        		continue;
        	}
        	for (Entity item : diagram.getEntities())
        		if (!entityToExclude.getName().equals(item.getName()))
        			entities.add(item);
        }
        return entities;
    }
    
    private Iterable<Hierarchy> getHierarchies(List<Diagram> diagrams) {
    	Set<Hierarchy> hierarchies = new HashSet<Hierarchy>();
    	for (Diagram diagram : diagrams)
        	for (Hierarchy item : diagram.getHierarchies())
        		hierarchies.add(item);
        return hierarchies;
    	
    }
    
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String value) {
		this.name = value;
	}
	
	public String getDataDirectory(){
		if (this.name != null){
			return this.name + "/" + SubFolder;
		}
		
		return null;
	}


	@Override
	public void addContextDiagram(Diagram diagram) {
		this.contextDiagram.add(diagram);
	}
	
	@Override
	public void addProjectDiagram(Diagram diagram) {
		this.projectDiagram.add(diagram);
	}

	@Override
	public void clearContextDiagrams() {
		this.contextDiagram.clear();
	}

	@Override
	public Entity getEntity(UUID id) {
		for (Diagram diagram : this.projectDiagram)
        	for (Entity item : diagram.getEntities())
        		if (item.getId().equals(id))
        			return item;
		return null;
	}

	@Override
	public Hierarchy getHierarchy(UUID id) {
		for (Diagram diagram : this.projectDiagram)
        	for (Hierarchy item : diagram.getHierarchies())
        		if (item.getId().equals(id))
        			return item;
		return null;
	}

	@Override
	public Diagram getContextDiagram(String diagramName) {
		return null;
	}
}
