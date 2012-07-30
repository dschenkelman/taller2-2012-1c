package validation;

import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;

import models.Diagram;
import models.Relationship;

public class EntitiesPerRelationshipValidator implements IValidator {
	@Override
	public Iterable<IValidationEntry> validate(Diagram diagram,
			Metrics metrics, int tolerance) {
		List<IValidationEntry> entries = new ArrayList<IValidationEntry>();
    	
        for (Relationship relationship : diagram.getRelationships()) {
        	int entitiesInRelationship = IterableExtensions.count(relationship.getRelationshipEntities());
        	if (!metrics.getAttributesPerRelationship().isInRange(entitiesInRelationship, tolerance)){
				entries.add(new EntitiesPerRelationshipValidationEntry(diagram, relationship, entitiesInRelationship));
			}
		}
        
    	return entries;
	}
}