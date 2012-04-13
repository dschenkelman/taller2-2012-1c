package models;

import java.util.UUID;

public class Entity implements INameable{

	private String name;
	private UUID id;
	private EntityType type;
	private AttributeCollection attributes;
	
	public Entity(String name) 
	{
		super();
		this.setName(name);
		this.setType(EntityType.None);
		this.id = UUID.randomUUID();
		this.attributes = new AttributeCollection();
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

	public AttributeCollection getAttributes() {
		return this.attributes;
	}
	
	
}
