package models;


import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.UUID;


public class DiagramType implements Iterable<DiagramType>{
	
	private UUID id;
	private EntityCollection entities;
	private List<Relationship> relationships;
	private HierarchyCollection hierarchies;
	private List<DiagramType> subDiagrams;
	
	public DiagramType()
	{
		this(UUID.randomUUID(), new EntityCollection(), 
				new ArrayList<Relationship>(), new HierarchyCollection(), 
				new ArrayList<DiagramType>());
	}

	public DiagramType(UUID id)
	{
		this(id, new EntityCollection(), new ArrayList<Relationship>(), 
				new HierarchyCollection(), new ArrayList<DiagramType>());
	}
	
	public DiagramType(EntityCollection entities,
			List<Relationship> relationships,
			HierarchyCollection hierarchies, List<DiagramType> subDiagrams)
	{
		this(UUID.randomUUID(), entities, relationships, hierarchies, 
				subDiagrams);
	}
	
	public DiagramType(UUID id, EntityCollection entities,
			List<Relationship> relationships,
			HierarchyCollection hierarchies, List<DiagramType> subDiagram) 
	{
		this.id = id;
		this.setEntities(entities);
		this.setRelationships(relationships);
		this.setHierarchies(hierarchies);
		this.setSubDiagrams(subDiagram);
	}

	public void setEntities(EntityCollection entities) {
		this.entities = entities;
	}

	public EntityCollection getEntities() {
		return entities;
	}

	public List<Relationship> getRelationships() {
		return relationships;
	}
	
	public void setRelationships(List<Relationship> relationships)
	{
		this.relationships = relationships;
	}

	public void setHierarchies(HierarchyCollection hierarchies) {
		this.hierarchies = hierarchies;
	}

	public HierarchyCollection getHierarchies() {
		return hierarchies;
	}

	public void setSubDiagrams(List<DiagramType> subDiagrams) {
		this.subDiagrams = subDiagrams;
	}

	public List<DiagramType> getSubDiagrams() {
		return subDiagrams;
	}

	public UUID getId() {
		return id;
	}
	
	public Relationship getRelationship(UUID relationshipId) {
		return IterableExtensions.firstOrDefault(this.relationships, 
				new RelationshipsCompFunc(), relationshipId); 
	}


	public boolean existsRelationship(UUID id) {
		return IterableExtensions.firstOrDefault(this.relationships,
				new RelationshipsCompFunc(), id) != null;
	}

	public void removeRelationship(UUID id) throws Exception {
		if (existsRelationship(id))
		{
			this.relationships.remove(this.getRelationship(id));
		}
		else
		{
			throw new Exception("Do not exists a relationship with id" + id);
		}
	}
	
	public void addSubDiagram(DiagramType subDiagram) {
		this.subDiagrams.add(subDiagram);
	}

	public DiagramType getSubDiagram(UUID id) {
		return IterableExtensions.firstOrDefault(this.subDiagrams, 
				new SubDiagramsCompFunc(), id);
	}

	public boolean existsSubDiagram(UUID id) 
	{
		return IterableExtensions.firstOrDefault(this.subDiagrams, 
				new SubDiagramsCompFunc(), id) != null;
	}

	public void removeSubDiagram(UUID id) throws Exception{		
		if (existsSubDiagram(id))
		{
			this.subDiagrams.remove(this.getSubDiagram(id));
		}
		else
		{
			throw new Exception("Do not exists an SubDiagram with id" + id);
		}
	}
	
	private class RelationshipsCompFunc extends 
	Func<Relationship, UUID, Boolean>
	{
		@Override
		public Boolean execute(Relationship relationship, UUID id) 
		{
			return relationship.getId().equals(id);
		}
		
	}
				
	private class SubDiagramsCompFunc extends
	Func<DiagramType, UUID, Boolean>
	{
		@Override
		public Boolean execute(DiagramType diagram, UUID id)
		{	
			return diagram.getId().equals(id);
		}
	}
	
	@Override
	public Iterator<DiagramType> iterator() {
		return this.subDiagrams.iterator();
	}
	
	
}
