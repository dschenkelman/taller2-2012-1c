package models;

import java.util.UUID;

public class Attribute implements INameable,IKey{
	private String name;
	private boolean isKeyField;
	private Cardinality minimumCardinality;
	private Cardinality maximumCardinality;
	private String expression;
	private IdGroupCollection idGroup;
	private AttributeType type;
    private UUID id;

    public Attribute(String name) {

		this.setName(name);
	}

	public Attribute(String name, boolean isKeyField,
			Cardinality minimumCardinality, Cardinality maximumCardinality,
			IdGroupCollection idGroup, AttributeType type, String expression) {

		this.name = name;
		this.isKeyField = isKeyField;
		this.minimumCardinality = minimumCardinality;
		this.maximumCardinality = maximumCardinality;
		this.idGroup = idGroup;
		this.type = type;
        this.id = UUID.randomUUID();
		if (expression != null) setExpression(expression); //Throws IllegalArgumentException
		
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

    @Override
    public UUID getOwnerId() {
        return null;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public Cardinality getMinimumCardinality() {
		return minimumCardinality;
	}

	public void setMinimumCardinality(Cardinality minimumCardinality) {
		this.minimumCardinality = minimumCardinality;
	}

	public Cardinality getMaximumCardinality() {
		return maximumCardinality;
	}

	public void setMaximumCardinality(Cardinality maximumCardinality) {
		this.maximumCardinality = maximumCardinality;
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
}
