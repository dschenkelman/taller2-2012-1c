package models;

import java.util.List;

public class AttributeCollection extends ModelCollection<Attribute> {

	public AttributeCollection() {
		super();
	}

	@Override
	protected Attribute createItemInstance(String itemName) {
		return new Attribute (itemName);
	}
	
	 
	
	
}
