package models;

public class Attribute implements INameable{
	private String name;
	private boolean isKeyField;
	private Cardinality minimumCardinality;
	private Cardinality maximumCardinality;
	private String expression;
	private IdGroupCollection idGroup;
	private AttributeType type;

	public Attribute(String name) {
		super();
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		// TODO: validar q el tipo de atributo sea copy o calculated sino lanzar
		// excepcion
		this.expression = expression;
	}

	public IdGroupCollection getIdGroup() {
		// TODO:
		return idGroup;
	}

	public void setIdGroup(IdGroupCollection idGroup) {
		this.idGroup = idGroup;
	}

}
