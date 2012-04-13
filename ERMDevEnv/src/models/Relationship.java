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
		this.attributes = new AttributeCollection();
		this.id = UUID.randomUUID();
		this.relationshipEntites = new ArrayList<RelationshipEntity>();
		if (!this.AddRelationshipEntity(entity1) || !this.AddRelationshipEntity(entity2))
		{
			throw new Exception();
		}
	}
	
	public Boolean AddRelationshipEntity(RelationshipEntity relationshipEntity)
	{
		for (RelationshipEntity relEntity : this.relationshipEntites)
		{
			if (relEntity.getEntity().getName() == relationshipEntity.getEntity().getName())
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

	public void setIsComposition(Boolean isComposition) {
		this.isComposition = isComposition;
	}

	public Boolean getIsComposition() {
		return isComposition;
	}

	public AttributeCollection getAttributes() {
		return this.attributes; 
	}

}
