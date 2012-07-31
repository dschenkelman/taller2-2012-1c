package validation;

import models.Diagram;

public class EntitiesPerDiagramValidationEntry implements IValidationEntry {

	private Diagram diagram;
	private int entityCount;

	public EntitiesPerDiagramValidationEntry(Diagram diagram, int entityCount){
		this.diagram = diagram;
		this.entityCount = entityCount;
	}
	
	@Override
	public String getValidation() {
		return String.format("Diagram %s has %d entities.", this.diagram.getName(), this.entityCount);
	}

	@Override
	public ValidationType getType() {
		return ValidationType.WARNING;
	}

}
