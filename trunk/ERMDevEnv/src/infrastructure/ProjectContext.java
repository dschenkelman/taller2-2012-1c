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
	
	private static String SubFolder = "Datos";
	
	private List<Diagram> projectDiagram;
	private List<Diagram> contextDiagram;

	private String name;

    public ProjectContext() {
    	this.projectDiagram = new ArrayList<Diagram>();
    	this.contextDiagram = new ArrayList<Diagram>();
    }

    @Override
    public Iterable<Entity> getAllEntities() {
    	return this.getAllEntities(null);
    }
    
    @Override
    public Iterable<Entity> getAllEntities(Entity entityToExclude) {
    	return this.getEntities(entityToExclude, this.projectDiagram);
    }
    
    /** 
     * Returns all entities from active diagram and all of his parents
     */
    @Override
    public Iterable<Entity> getFamilyEntities() {
       return this.getEntities(null, this.contextDiagram);
    }
    
    /** 
     * Returns all entities from active diagram and all of his parents
     */
    @Override
    public Iterable<Entity> getFamilyEntities(Entity entityToExclude) {
       return this.getEntities(entityToExclude, this.contextDiagram);
    }
    
    /**
     * Returns all entities of active diagram
     */
    @Override
    public Iterable<Entity> getContextEntities() {
    	return this.getEntities(null, null);
	}
    
    /**
     * Returns all hierarchies from active diagram and all of his parents
     */
    @Override
    public Iterable<Hierarchy> getFamilyHierarchies() {
    	return this.getHierarchies(this.contextDiagram);
    }
    
    /**
     * Returns all hierarchies of active diagram
     */
    @Override
    public Iterable<Hierarchy> getContextHierarchies() {
    	return this.getHierarchies(null);
    }
    
    @Override
    public Iterable<Hierarchy> getAllHierarchies() {
    	return this.getHierarchies(this.projectDiagram);
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
		for (Diagram diagram : this.projectDiagram)
			if (diagram.getName().equals(diagramName))
				return diagram;
		return null;
	}
	
	private Iterable<Entity> getEntities(Entity entityToExclude, List<Diagram> diagrams) {
    	Set<Entity> entities = new HashSet<Entity>();
    	if (diagrams == null) {
    		if (this.contextDiagram.isEmpty())
    			return entities;
    		int size = this.contextDiagram.size();
    		for (Entity item : this.contextDiagram.get(size - 1).getEntities())
    			entities.add(item);
    	}else {
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
    	}
        return entities;
    }
    
    private Iterable<Hierarchy> getHierarchies(List<Diagram> diagrams) {
    	Set<Hierarchy> hierarchies = new HashSet<Hierarchy>();
    	if (diagrams == null) {
    		if (this.contextDiagram.isEmpty())
    			return hierarchies;
    		int size = this.contextDiagram.size();
    		for (Hierarchy item : this.contextDiagram.get(size - 1).getHierarchies())
        		hierarchies.add(item);
    	}else {
	    	for (Diagram diagram : diagrams)
	        	for (Hierarchy item : diagram.getHierarchies())
	        		hierarchies.add(item);
    	}
        return hierarchies;
    	
    }
}
