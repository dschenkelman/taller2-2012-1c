package validation;

public class Metrics {
	
	private MetricPair attributesPerEntity;
	private MetricPair attributesPerRelationship;
	private MetricPair entitiesPerDiagram;
	private MetricPair entitiesPerRelationship;
	private MetricPair relationshipsPerDiagram;
	private MetricPair entitiesPerHierarchy;
	private MetricPair hierarchiesPerDiagram;
	
	public MetricPair getAttributesPerEntity() {
		return attributesPerEntity;
	}
	
	public void setAttributesPerEntity(MetricPair attributesPerEntity) {
		this.attributesPerEntity = attributesPerEntity;
	}
	
	public MetricPair getAttributesPerRelationship() {
		return attributesPerRelationship;
	}
	public void setAttributesPerRelationship(MetricPair attributesPerRelationship) {
		this.attributesPerRelationship = attributesPerRelationship;
	}
	public MetricPair getEntitiesPerDiagram() {
		return entitiesPerDiagram;
	}
	public void setEntitiesPerDiagram(MetricPair entitiesPerDiagram) {
		this.entitiesPerDiagram = entitiesPerDiagram;
	}
	public MetricPair getEntitiesPerRelationship() {
		return entitiesPerRelationship;
	}
	public void setEntitiesPerRelationship(MetricPair entitiesPerRelationship) {
		this.entitiesPerRelationship = entitiesPerRelationship;
	}
	public MetricPair getRelationshipsPerDiagram() {
		return relationshipsPerDiagram;
	}
	public void setRelationshipsPerDiagram(MetricPair relationshipsPerDiagram) {
		this.relationshipsPerDiagram = relationshipsPerDiagram;
	}
	public MetricPair getEntitiesPerHierarchy() {
		return entitiesPerHierarchy;
	}
	public void setEntitiesPerHierarchy(MetricPair entitiesPerHierarchy) {
		this.entitiesPerHierarchy = entitiesPerHierarchy;
	}
	public MetricPair getHierarchiesPerDiagram() {
		return hierarchiesPerDiagram;
	}
	public void setHierarchiesPerDiagram(MetricPair hierarchiesPerDiagram) {
		this.hierarchiesPerDiagram = hierarchiesPerDiagram;
	}
}
