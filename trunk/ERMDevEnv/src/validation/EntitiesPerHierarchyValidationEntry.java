package validation;

import models.Diagram;
import models.Hierarchy;
import models.Relationship;

public class EntitiesPerHierarchyValidationEntry implements IValidationEntry {

	private Diagram diagram;
	private Hierarchy hierarchy;
	private int entitiesInHierarchy;

	public EntitiesPerHierarchyValidationEntry(Diagram diagram,
			Hierarchy hierarchy, int entitiesInHierarchy) 
	{
		this.diagram = diagram;
		this.hierarchy = hierarchy;
		this.entitiesInHierarchy = entitiesInHierarchy;
	}

	@Override
	public String getValidation() {
		return String.format("Hierarchy %s in diagram %s has %d entities.", this.hierarchy.toString(), this.diagram.getName(), this.entitiesInHierarchy);
	}

	@Override
	public ValidationType getType() {
		return ValidationType.WARNING;
	}

}
