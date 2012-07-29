package validation;

import models.Diagram;

public interface IMetricsCalculator {
	Metrics calculateMetrics(Iterable<Diagram> diagrams);
}
