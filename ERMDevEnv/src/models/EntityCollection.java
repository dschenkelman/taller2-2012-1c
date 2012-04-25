package models;

import java.util.List;

public class EntityCollection extends ModelCollection<Entity>{
	
	public EntityCollection () {
		super ();
	}
	
	
	public boolean add(String entityName, EntityType entityType) {
		return this.add(new Entity(entityName, entityType));
	}
	
	public boolean add(Entity entity)
	{
		if (this.get(entity.getName()) == null)
		{
			this.items.add(entity);
		}
		return false;
	}
	
	@Override
	public Entity createItemInstance (String entityName) {
		return new Entity(entityName);
	}	
}
