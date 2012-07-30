package validation;

public class MetricPair {
	private double mean;
	private double standardDeviation;
	
	public MetricPair(double mean){
		this.mean = mean;
	}
	
	public MetricPair(double mean, double deviation){
		this.mean = mean;
		this.standardDeviation = deviation;
	}
	
	public double getStandardDeviation() {
		return standardDeviation;
	}
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	
	public boolean isInRange(double value, int deviationsCount){
		return value >= this.mean - deviationsCount * this.standardDeviation 
				&& value <= this.mean + deviationsCount * this.standardDeviation;
	}
}
