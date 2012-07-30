package validation;

import models.Diagram;
import models.Entity;
import models.Relationship;

public class AttributesPerRelationshipValidationEntry implements IValidationEntry {
	private Diagram diagram;
	private Relationship relationship;
	private int attributeCount;
	
	public AttributesPerRelationshipValidationEntry(Diagram diagram, Relationship relationship, int attributeCount){
		this.diagram = diagram;
		this.relationship = relationship;
		this.attributeCount = attributeCount;
	}
	
	@Override
	public String getValidation() {
		return String.format("Relationship %s in diagram %s has %d attributes.", this.relationship.getName(), diagram.getName(), attributeCount);
	}

	@Override
	public ValidationType getType() {
		return ValidationType.WARNING;
	}
}
