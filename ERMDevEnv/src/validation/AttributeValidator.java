package validation;

import java.util.ArrayList;
import java.util.List;

import models.Diagram;
import models.Entity;

public class AttributeValidator implements IValidator {
    @Override
    public Iterable<IValidationEntry> validate(Diagram diagram, Metrics metrics, int tolerance) {
        List<IValidationEntry> entries = new ArrayList<IValidationEntry>();
    	
        for (Entity entity : diagram.getEntities()) {
        	int attributesInEntity = MetricsCalculator.getAttributeCount(entity.getAttributes());
        	if (!metrics.getAttributesPerEntity().isInRange(attributesInEntity, tolerance)){
				entries.add(new AttributeValidationEntry(diagram, entity, attributesInEntity));
			}
		}
        
    	return entries;
    }
}
