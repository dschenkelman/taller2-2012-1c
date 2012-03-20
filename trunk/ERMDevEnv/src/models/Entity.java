package models;

import java.util.ArrayList;
import java.util.List;

public class Entity {

	private String name;
	private List<Field> fields;

	public Entity(String name) 
	{
		this.setName(name);
		this.fields = new ArrayList<Field>();
	}

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Field> getFields()
	{
		return this.fields;
	}
}
