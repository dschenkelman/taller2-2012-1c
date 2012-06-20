package infrastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Diagram;
import models.Entity;
import models.Hierarchy;
import models.IStrongEntity;

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
    public Iterable<Entity> getAllEntities(Entity entityToExclude) {
    	return this.getEntities(entityToExclude, this.projectDiagram);
    }
    
    @Override
    public Iterable<Entity> getContextEntities(Entity entityToExclude) {
       return this.getEntities(entityToExclude, this.contextDiagram);
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
    public Iterable<IStrongEntity> getPossibleStrongEntities(
            List<IStrongEntity> strongEntitiesToExclude) {
        // TODO Auto-generated method stub
        return null;
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
	public void clear() {
		this.contextDiagram.clear();
	}
}
