package validation;

import java.util.ArrayList;
import java.util.List;

import models.Diagram;
import models.Entity;
import models.Relationship;

public class AttributesPerRelationshipValidator implements IValidator {
	@Override
    public Iterable<IValidationEntry> validate(Diagram diagram, Metrics metrics, int tolerance) {
        List<IValidationEntry> entries = new ArrayList<IValidationEntry>();
    	
        for (Relationship relationship : diagram.getRelationships()) {
        	int attributesInEntity = MetricsCalculator.getAttributeCount(relationship.getAttributes());
        	if (!metrics.getAttributesPerRelationship().isInRange(attributesInEntity, tolerance)){
				entries.add(new AttributesPerRelationshipValidationEntry(diagram, relationship, attributesInEntity));
			}
		}
        
    	return entries;
    }
}
