package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

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

	public void setName(String name) 
	{
		this.name = name;
	}

	public Iterable<Field> getFields()
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
		class FieldCmpFunc extends Func<Field, String, Boolean>
		{
			@Override
			public Boolean execute(Field field, String n) 
			{
				return field.getName().equals(n);
			}
		}
		
		return IterableExtensions.firstOrDefault(this.fields, new FieldCmpFunc(), name);
	}
}
