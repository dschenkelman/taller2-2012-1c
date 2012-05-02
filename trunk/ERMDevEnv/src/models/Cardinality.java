package models;

public class Cardinality {

	private double maximum;
	private double minimum;

	public Cardinality(double minimum, double maximum) throws Exception {
		this.setMaximum(maximum);
		this.setMinimum(minimum);
	}

	public void setMinimum(double min) throws Exception {
		if (this.maximum < min) {
			throw new Exception();
		}

		this.minimum = min;
	}

	public double getMinimum() {
		return minimum;
	}

	public void setMaximum(double max) throws Exception {
		if (max < this.minimum) {
			throw new Exception();
		}

		this.maximum = max;
	}

	public double getMaximum() {
		return maximum;
	}

	public static double getCardinalityFromString(String value) {
		return value.equalsIgnoreCase("*") ? Double.POSITIVE_INFINITY : Double.parseDouble(value);
	}

	public static String getStringForCardinality(double value) {
		return value == Double.POSITIVE_INFINITY ? "*" : Integer.toString((int) value);
	}

}
