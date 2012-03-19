package models;

import java.util.ArrayList;
import java.util.List;

public class Entity {

	private String name;
	private List<Object> relationships;
	private List<Object> fields;

	public Entity(String name) 
	{
		this.setName(name);
		this.fields = new ArrayList<Object>();
		this.relationships = new ArrayList<Object>();
	}

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getRelationships() 
	{
		return this.relationships;
	}

	public List<Object> getFields()
	{
		return this.fields;
	}
}
