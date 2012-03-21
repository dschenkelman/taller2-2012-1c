package models;

public class Field extends FieldContainer
{
	private String name;
	private boolean isKeyField;

	public Field(String name)
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
