package models;

import java.util.ArrayList;
import java.util.Collection;
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

	public void setName(String name) 
	{
		this.name = name;
	}

	public Collection<Field> getFields()
	{
		return this.fields;
	}

	public boolean addField(String name) 
	{
		if (this.getField(name) == null)
		{
			return this.fields.add(new Field(name));
		}
		
		return false;
	}

	public Field getField(String name)
	{
		for (Field field : this.fields) 
		{
			if (field.getName().equalsIgnoreCase(name))
			{
				return field;
			}
		}
		
		return null;
	}
}
