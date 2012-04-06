package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;

public class EntityCollection {
	private List<Entity> entities;

	public EntityCollection() {
		this.entities = new ArrayList<Entity>();
	}

	public int count() {
		return this.entities.size();
	}

	public boolean add(String entityName) {
		return this.add(entityName, EntityType.None);
	}

	public boolean add(String entityName, EntityType entityType) {
		if (this.get(entityName) == null) 
		{
			Entity entity = new Entity(entityName);
			entity.setType(entityType);
			this.entities.add(entity);
			return true;
		}
		return false;
	}
	
	public Entity get(String entityName) {
		return IterableExtensions.firstOrDefault(this.entities,
				new Func<Entity, String, Boolean>() {
					@Override
					public Boolean execute(Entity entity, String entityName) {
						return entity.getName() == entityName;
					}
				}, entityName);
	}	

	public boolean remove(String entityName) {
		Entity entity = this.get(entityName);
		return this.entities.remove(entity);
	}

}
