package models;

public class RelationshipEntity {
	private Entity entity;
	private Cardinality cardinality;
	private String role;
	
	
	public RelationshipEntity(Entity entity) {
		super();
		this.entity = entity;
		this.setCardinality(null);
		this.setRole(null);
	}

	public RelationshipEntity(Entity entity, Cardinality cardinality,
			String role) {
		super();
		this.entity = entity;
		this.cardinality = cardinality;
		this.role = role;
		
	}

	public void setCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
	}

	public Cardinality getCardinality() {
		return this.cardinality;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return this.role;
	}

	public Entity getEntity() {
		return this.entity;
	}
	
}
