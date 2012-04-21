package models;

import java.util.List;
import java.util.UUID;

public class DiagramType {
	
	private UUID id;
	private EntityCollection entities;
	private List<Relationship> relationships;
	private HierarchyCollection hierarchies;
	private DiagramType subDiagram;
	
	public DiagramType()
	{
		this.id = UUID.randomUUID();
		this.setEntities(null);
		this.setRelationships(null);
		this.setHierarchies(null);
		this.setSubDiagram(null);
	}

	public DiagramType(EntityCollection entities,
			List<Relationship> relationshipCollection,
			HierarchyCollection hierarchies, DiagramType subDiagram) 
	{
		this.id = UUID.randomUUID();
		this.setEntities(entities);
		this.setRelationships(relationshipCollection);
		this.setHierarchies(hierarchies);
		this.setSubDiagram(subDiagram);
	}

	public void setEntities(EntityCollection entities) {
		this.entities = entities;
	}

	public EntityCollection getEntities() {
		return entities;
	}

	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}

	public List<Relationship> getRelationships() {
		return relationships;
	}

	public void setHierarchies(HierarchyCollection hierarchies) {
		this.hierarchies = hierarchies;
	}

	public HierarchyCollection getHierarchies() {
		return hierarchies;
	}

	public void setSubDiagram(DiagramType subDiagram) {
		this.subDiagram = subDiagram;
	}

	public DiagramType getSubDiagram() {
		return subDiagram;
	}

	public Object getId() {
		return id;
	}
}
