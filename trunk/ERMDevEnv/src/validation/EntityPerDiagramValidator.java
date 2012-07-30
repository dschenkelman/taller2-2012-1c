package validation;

import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;

import models.Diagram;
import models.Entity;

public class EntityPerDiagramValidator implements IValidator {

	@Override
	public Iterable<IValidationEntry> validate(Diagram diagram,
			Metrics metrics, int tolerance) {
		List<IValidationEntry> entries = new ArrayList<IValidationEntry>();
		
		 int entityCount = IterableExtensions.count(diagram.getEntities());
		 
		 if (!metrics.getEntitiesPerDiagram().isInRange(entityCount, tolerance)){
				entries.add(new EntityPerDiagramValidationEntry(diagram, entityCount));
		 }
		 
		 return entries;
	}

}
