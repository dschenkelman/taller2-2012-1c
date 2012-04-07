package models;

public class EntityCollection extends ModelCollection<Entity>{
	
	public EntityCollection () {
		super ();
	}
	
	
	public boolean add(String entityName, EntityType entityType) {
		if (this.get(entityName) == null) 
		{
			Entity entity = new Entity(entityName);
			entity.setType(entityType);
			this.items.add(entity);
			return true;
		}
		return false;
	}
	
	@Override
	public Entity createItemInstance (String entityName) {
		return new Entity(entityName);
	}
		
}
