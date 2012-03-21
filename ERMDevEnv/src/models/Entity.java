package models;

public class Entity extends FieldContainer {

	private String name;
	
	public Entity(String name) 
	{
		super();
		this.setName(name);
	}

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
}
