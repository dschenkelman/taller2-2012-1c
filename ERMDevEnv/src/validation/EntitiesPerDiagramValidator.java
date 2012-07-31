package validation;

import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;

import models.Diagram;

public class EntitiesPerDiagramValidator implements IValidator {

	@Override
	public Iterable<IValidationEntry> validate(Diagram diagram,
			Metrics metrics, int tolerance) {
		List<IValidationEntry> entries = new ArrayList<IValidationEntry>();
		
		 int entityCount = IterableExtensions.count(diagram.getEntities());
		 
		 if (!metrics.getEntitiesPerDiagram().isInRange(entityCount, tolerance)){
				entries.add(new EntitiesPerDiagramValidationEntry(diagram, entityCount));
		 }
		 
		 return entries;
	}

}
