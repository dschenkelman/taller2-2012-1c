package models;

import java.util.ArrayList;
import java.util.List;

public class Field {

	private String name;
	private boolean isKeyField;
	private List<Field> children;

	public Field(String name) {
		this.setName(name);
		this.children = new ArrayList<Field>();
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

	public List<Field> getChildren() 
	{
		return this.children;
	}
}
