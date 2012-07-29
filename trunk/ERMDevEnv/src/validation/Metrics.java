package validation;

public class Metrics {
	private double attributesPerEntity;
	private double attributesPerRelationship;
	private double entitiesPerDiagram;
	private double entitiesPerRelationship;
	private double relationshipsPerDiagram;
	private double entitiesPerHierarchy;
	private double hierarchiesPerDiagram;
	
	public double getAttributesPerEntity() {
		return attributesPerEntity;
	}
	public void setAttributesPerEntity(double attributesPerEntity) {
		this.attributesPerEntity = attributesPerEntity;
	}
	public double getAttributesPerRelationship() {
		return attributesPerRelationship;
	}
	public void setAttributesPerRelationship(double attributesPerRelationship) {
		this.attributesPerRelationship = attributesPerRelationship;
	}
	public double getEntitiesPerDiagram() {
		return entitiesPerDiagram;
	}
	public void setEntitiesPerDiagram(double entitiesPerDiagram) {
		this.entitiesPerDiagram = entitiesPerDiagram;
	}
	public double getEntitiesPerRelationship() {
		return entitiesPerRelationship;
	}
	public void setEntitiesPerRelationship(double entitiesPerRelationship) {
		this.entitiesPerRelationship = entitiesPerRelationship;
	}
	public double getRelationshipsPerDiagram() {
		return relationshipsPerDiagram;
	}
	public void setRelationshipsPerDiagram(double relationshipsPerDiagram) {
		this.relationshipsPerDiagram = relationshipsPerDiagram;
	}
	public double getEntitiesPerHierarchy() {
		return entitiesPerHierarchy;
	}
	public void setEntitiesPerHierarchy(double entitiesPerHierarchy) {
		this.entitiesPerHierarchy = entitiesPerHierarchy;
	}
	public double getHierarchiesPerDiagram() {
		return hierarchiesPerDiagram;
	}
	public void setHierarchiesPerDiagram(double hierarchiesPerDiagram) {
		this.hierarchiesPerDiagram = hierarchiesPerDiagram;
	}
}
