package models;

public class Attribute
{
	private String name;
	private boolean isKeyField;

	public Attribute(String name)
	{
		super();
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() 
	{
		return this.name;
	}

	public void isKey(boolean value) {
		this.isKeyField = value;
	}
	
	public boolean isKey() 
	{
		return this.isKeyField;
	}
}
