package models;

import java.util.UUID;

public class Entity implements INameable{

	private String name;
	private UUID id;
	private EntityType type;
	
	public Entity(String name) 
	{
		super();
		this.setName(name);
		this.setType(EntityType.None);
		this.id = UUID.randomUUID();
		
	}

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public UUID getId() {
		return this.id;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public EntityType getType() {
		return type;
	}
	
	
}
