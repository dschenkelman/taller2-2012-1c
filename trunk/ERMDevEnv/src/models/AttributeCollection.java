package models;

public class AttributeCollection extends ModelCollection<Attribute> {

	public AttributeCollection() {
		super();
	}

	@Override
	protected Attribute createItemInstance(String itemName) {
		return new Attribute (itemName);
	}
	
	 
	
	
}
