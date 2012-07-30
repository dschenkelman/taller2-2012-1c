package validation;

import models.Diagram;
import models.Relationship;

public class EntitiesPerRelationshipValidationEntry implements IValidationEntry {

	private Diagram diagram;
	private Relationship relationship;
	private int entitiesInRelationship;

	public EntitiesPerRelationshipValidationEntry(Diagram diagram,
			Relationship relationship, int entitiesInRelationship) 
	{
		this.diagram = diagram;
		this.relationship = relationship;
		this.entitiesInRelationship = entitiesInRelationship;
	}

	@Override
	public String getValidation() {
		return String.format("Relationship %s in diagram %s has %d entities.", this.relationship.getName(), this.diagram.getName(), this.entitiesInRelationship);
	}

	@Override
	public ValidationType getType() {
		return ValidationType.WARNING;
	}

}
