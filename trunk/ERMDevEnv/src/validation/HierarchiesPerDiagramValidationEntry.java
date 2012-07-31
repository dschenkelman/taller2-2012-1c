package validation;

import models.Diagram;

public class HierarchiesPerDiagramValidationEntry implements IValidationEntry {

	private Diagram diagram;
	private int hierarchyCount;

	public HierarchiesPerDiagramValidationEntry(Diagram diagram, int hierarchyCount){
		this.diagram = diagram;
		this.hierarchyCount = hierarchyCount;
	}
	
	@Override
	public String getValidation() {
		return String.format("Diagram %s has %d hierarchies.", this.diagram.getName(), this.hierarchyCount);
	}

	@Override
	public ValidationType getType() {
		return ValidationType.WARNING;
	}


}
