package validation;

import infrastructure.IterableExtensions;
import models.Attribute;
import models.Diagram;
import models.Entity;
import models.Hierarchy;
import models.Relationship;

public class MetricsCalculator implements IMetricsCalculator {
	@Override
	public Metrics calculateMetrics(Iterable<Diagram> diagrams) {
		double diagramCount = IterableExtensions.count(diagrams);
		
		double entities = 0;
		double attributesInEntities = 0;
		double entitiesInRelationships = 0;
		double attributesInRelationships = 0;
		double relationships = 0;
		double hierarchies = 0;
		double entitiesInHierarchies = 0;		
		
		for (Diagram diagram : diagrams) {
			for (Entity entity : diagram.getEntities()) {
				attributesInEntities += this.getAttributeCount(entity.getAttributes());
				entities++;
			}
			
			for (Relationship relationship : diagram.getRelationships()) {
				entitiesInRelationships += IterableExtensions.count(relationship.getRelationshipEntities());
				attributesInRelationships += this.getAttributeCount(relationship.getAttributes());
				relationships++;
			}
			
			for (Hierarchy hierarchy : diagram.getHierarchies()) {
				entitiesInHierarchies += IterableExtensions.count(hierarchy.getChildren());
				hierarchies++;
			}
		}
		
		Metrics metrics = new Metrics();
		
		metrics.setAttributesPerEntity(attributesInEntities / entities);
		metrics.setAttributesPerRelationship(attributesInRelationships / relationships);
		metrics.setEntitiesPerDiagram(entities / diagramCount);
		metrics.setEntitiesPerHierarchy(entitiesInHierarchies / hierarchies);
		metrics.setEntitiesPerRelationship(entitiesInRelationships / relationships);
		metrics.setHierarchiesPerDiagram(hierarchies / diagramCount);
		metrics.setRelationshipsPerDiagram(relationships / diagramCount);
		
		return metrics;
	}
	
	private int getAttributeCount(Iterable<Attribute> attributes){
		int count = 0;
		
		for (Attribute attribute : attributes) {
			count++;
			
			count += getAttributeCount(attribute.getAttributes());
		}
		
		return count;
	}
}
