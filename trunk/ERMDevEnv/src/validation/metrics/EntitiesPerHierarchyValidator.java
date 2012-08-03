package validation.metrics;

import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;

import validation.IValidationEntry;

import models.Diagram;
import models.Hierarchy;
import models.Relationship;

public class EntitiesPerHierarchyValidator implements IMetricsValidator {
	@Override
	public Iterable<IValidationEntry> validate(Diagram diagram,
			Metrics metrics, int tolerance) {
		List<IValidationEntry> entries = new ArrayList<IValidationEntry>();
    	
        for (Hierarchy hierarchy : diagram.getHierarchies()) {
        	int entitiesInHierarchy = IterableExtensions.count(hierarchy.getChildren());
        	if (!metrics.getEntitiesPerHierarchy().isInRange(entitiesInHierarchy, tolerance)){
				entries.add(new EntitiesPerHierarchyValidationEntry(diagram, hierarchy, entitiesInHierarchy));
			}
		}
        
    	return entries;
	}
}
