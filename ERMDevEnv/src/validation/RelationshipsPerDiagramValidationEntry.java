package validation;

import models.Diagram;

public class RelationshipsPerDiagramValidationEntry implements IValidationEntry {

	private int relationshipCount;
	private Diagram diagram;

	public RelationshipsPerDiagramValidationEntry(Diagram diagram,
			int relationshipCount) {
		this.diagram = diagram;
		this.relationshipCount = relationshipCount;
	}

	@Override
	public String getValidation() {
		return String.format("Diagram %s has %d relationships.", this.diagram.getName(), this.relationshipCount);
	}

	@Override
	public ValidationType getType() {
		return ValidationType.WARNING;
	}
}
