package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;

public abstract class FieldContainer 
{
	protected List<Field> fields;
	
	public FieldContainer() 
	{
		this.fields = new ArrayList<Field>();
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
	

	public boolean removeField(String name) 
	{
		Field  field = this.getField(name);
		if (field == null)
		{
			return false;
		}
		
		return this.fields.remove(field);
	}
}
