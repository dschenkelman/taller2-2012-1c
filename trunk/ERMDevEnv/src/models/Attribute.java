package models;

import java.util.UUID;

public class Attribute implements INameable, IKey {
	private String name;
	private boolean isKeyField;
	private Cardinality cardinality;
	private String expression;
	private AttributeType type;

	private IdGroupCollection idGroup;
	private AttributeCollection attributes;//TODO: cambiar esto para que pueda ser accedido;
	
	private UUID myID;
	private UUID ownerId;
	
	public Attribute(String name) {

		this.setName(name);
		myID = UUID.randomUUID();
	}

	
	public Attribute(String name, boolean isKeyField, Cardinality cardinality, IdGroupCollection idGroup, 
			AttributeType type, String expression) {

		this(name);
		this.isKeyField = isKeyField;
		this.setCardinality(cardinality);
		this.idGroup = idGroup;
		this.type = type;
		
		if (expression != null) setExpression(expression); //Throws IllegalArgumentException
		
	}
	
	public Attribute(String name, boolean isKeyField, Cardinality cardinality, IdGroupCollection idGroup, 
			AttributeType type, String expression,AttributeCollection attCol ,UUID ownerID, UUID myID) {
		this (name, isKeyField, cardinality, idGroup, type, expression);
		this.attributes=attCol;
		this.ownerId = ownerID;
		if (myID != null) {
			this.myID = myID;
		}
		
	}
	
	

	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	

	public void isKey(boolean value) {
		this.isKeyField = value;
	}

	public boolean isKey() {
		return this.isKeyField;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

	public AttributeType getType() {
		return type;
	}

	public Cardinality getCardinality() {
		return cardinality;
	}

	public void setCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
	}

	public String getExpression() throws IllegalArgumentException {
		if (this.type == AttributeType.calculated
				|| this.type == AttributeType.copy)
			return expression;
		else
			throw new IllegalArgumentException();
	}

	public void setExpression(String expression) throws IllegalArgumentException {
		if (this.type == AttributeType.calculated
				|| this.type == AttributeType.copy)
			this.expression = expression;
		else
			throw new IllegalArgumentException();
	}

	public IdGroupCollection getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(IdGroupCollection idGroup) {
		this.idGroup = idGroup;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public AttributeCollection getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeCollection attributes) {
		this.attributes = attributes;
	}

	public void setId(UUID id) {
		myID = id;
	}
	
	@Override
	public UUID getId() {
		return myID;
	}
}
