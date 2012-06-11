package infrastructure;

import java.util.ArrayList;
import java.util.List;

import models.Entity;
import models.IStrongEntity;

public class ProjectContext implements IProjectContext {

	private static String SubFolder = "Datos";
	
    List<Entity> entities;
	private String name;

    public ProjectContext() {
        this.entities = new ArrayList<Entity>();
    }

    @Override
    public Iterable<Entity> getEntityCollection(Entity entityToExclude) {
        if (entityToExclude.getName().equals(""))
            return this.entities;
        return this.cloneList(entityToExclude);
    }

    @Override
    public Iterable<IStrongEntity> getPossibleStrongEntities(
            List<IStrongEntity> strongEntitiesToExclude) {
        // TODO Auto-generated method stub
        return null;
    }

    private List<Entity> cloneList(Entity entityToExclude) {
        List<Entity> clone = new ArrayList<Entity>();
        for (Entity item : this.entities) {
            if (!entityToExclude.getName().equals(item.getName())) {
                Entity entity = new Entity(item.getName(), item.getId(), item.getType(), item.getAttributes());
                clone.add(entity);
            }
        }
        return clone;
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

}
