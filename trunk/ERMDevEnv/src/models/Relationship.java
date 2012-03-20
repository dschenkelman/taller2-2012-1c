package models;

public class Relationship {

	private Entity firstEntity;
	private Entity secondEntity;
	private Cardinality firstCardinality;
	private Cardinality secondCardinality;

	public Relationship(Entity firstEntity, Entity secondEntity) throws Exception 
	{
		this.setFirstEntity(firstEntity);
		this.setSecondEntity(secondEntity);
		this.setFirstCardinality(new Cardinality(1, 1));
		this.setSecondCardinality(new Cardinality(1, 1));
	}
	
	public void setFirstEntity(Entity firstEntity) {
		this.firstEntity = firstEntity;
	}

	public Entity getFirstEntity() {
		return firstEntity;
	}

	public void setSecondEntity(Entity secondEntity) {
		this.secondEntity = secondEntity;
	}

	public Entity getSecondEntity() {
		return secondEntity;
	}

	public void setSecondCardinality(Cardinality secondCardinality) {
		this.secondCardinality = secondCardinality;
	}

	public void setFirstCardinality(Cardinality firstCardinality) {
		this.firstCardinality = firstCardinality;
	}
	

	public Cardinality getFirstCardinality() {
		return this.firstCardinality;
	}

	public Cardinality getSecondCardinality() {
		return this.secondCardinality;
	}
}
