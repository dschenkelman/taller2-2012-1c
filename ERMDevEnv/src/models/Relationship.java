package models;

public class Relationship {

	private double maximumCardinality;
	private double minimumCardinality;
	private Entity relatedEntity;

	public Relationship(Entity relatedEntity, 
			double minimumCardinality, 
			double maximumCardinality) throws Exception 
	{
		if (maximumCardinality < minimumCardinality)
		{
			throw new Exception();
		}
		
		this.setRelatedEntity(relatedEntity);
		this.setMinimumCardinality(minimumCardinality);
		this.setMaximumCardinality(maximumCardinality);
	}

	public void setMaximumCardinality(double maximumCardinality) {
		this.maximumCardinality = maximumCardinality;
	}

	public double getMaximumCardinality() {
		return maximumCardinality;
	}

	public void setMinimumCardinality(double minimumCardinality) {
		this.minimumCardinality = minimumCardinality;
	}

	public double getMinimumCardinality() {
		return minimumCardinality;
	}

	public void setRelatedEntity(Entity relatedEntity) {
		this.relatedEntity = relatedEntity;
	}

	public Entity getRelatedEntity() {
		return relatedEntity;
	}
}
