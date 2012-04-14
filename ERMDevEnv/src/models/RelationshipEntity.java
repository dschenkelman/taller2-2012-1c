package models;

import java.util.UUID;

public class RelationshipEntity {
	private Cardinality cardinality;
	private String role;
	private UUID entityId;
	
	
	public RelationshipEntity(Entity entity) {
		this(entity.getId(), null, null);
	}

	public RelationshipEntity(Entity entity, Cardinality cardinality,
			String role) {		
		this(entity.getId(), cardinality, role);
	}

	public RelationshipEntity(UUID id, Cardinality cardinality, String role) {
		super();
		this.entityId = id;
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

	public UUID getEntityId() {
		return this.entityId;
	}
	
}
