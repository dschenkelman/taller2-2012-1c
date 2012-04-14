package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Relationship {

	private List<RelationshipEntity> relationshipEntites;
	private UUID id;
	private Boolean isComposition;
	private String name;
	private AttributeCollection attributes;
	
	public Relationship(RelationshipEntity entity1, RelationshipEntity entity2) throws Exception 
	{
		this(UUID.randomUUID(), null, false);
		if (!this.addRelationshipEntity(entity1) || !this.addRelationshipEntity(entity2))
		{
			throw new Exception();
		}
	}
	
	public Relationship(UUID id, String name, Boolean isComposition) {
		this.attributes = new AttributeCollection();
		this.relationshipEntites = new ArrayList<RelationshipEntity>();
		this.id = id;
		this.isComposition = isComposition;
		this.name = name;
	}

	public Boolean addRelationshipEntity(RelationshipEntity relationshipEntity)
	{
		for (RelationshipEntity relEntity : this.relationshipEntites)
		{
			if (relEntity.getEntityId() == relationshipEntity.getEntityId())
			{
				String role1 = relationshipEntity.getRole();
				String role2 = relEntity.getRole();
				
				if (role1 == null || role1 == "" || role2 == null || role2 == "" || role1 == role2)
				{
					return false;
				}
			}
		}
		
		this.relationshipEntites.add(relationshipEntity);
		return true;
	}

	public Iterable<RelationshipEntity> getRelationshipEntities() 
	{
		return this.relationshipEntites;
	}

	public UUID getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void isComposition(Boolean isComposition) {
		this.isComposition = isComposition;
	}

	public Boolean isComposition() {
		return isComposition;
	}

	public AttributeCollection getAttributes() {
		return this.attributes; 
	}

}
