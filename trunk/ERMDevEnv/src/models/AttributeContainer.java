package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;

public abstract class AttributeContainer
{
	protected List<Attribute> attributes;
	
	public AttributeContainer()
	{
		this.attributes = new ArrayList<Attribute>();
	}
	
	public Iterable<Attribute> getAttributes()
	{
		return this.attributes;
	}

	public boolean addField(String name) 
	{
		if (this.getField(name) == null)
		{
			return this.attributes.add(new Attribute(name));
		}
		
		return false;
	}

	public Attribute getField(String name)
	{
		class FieldCmpFunc extends Func<Attribute, String, Boolean>
		{
			@Override
			public Boolean execute(Attribute field, String n)
			{
				return field.getName().equals(n);
			}
		}
		
		return IterableExtensions.firstOrDefault(this.attributes, new FieldCmpFunc(), name);
	}
	

	public boolean removeField(String name) 
	{
		Attribute attribute = this.getField(name);
		if (attribute == null)
		{
			return false;
		}
		
		return this.attributes.remove(attribute);
	}
}
