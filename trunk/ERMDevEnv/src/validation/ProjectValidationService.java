package validation;

import java.util.ArrayList;
import java.util.List;

import models.Diagram;

public class ProjectValidationService implements IProjectValidationService {

	private IMetricsCalculator metricsCalculator;
	private IValidator[] validators;

	public ProjectValidationService(IMetricsCalculator metricsCalculator, IValidator[] validators){
		this.metricsCalculator = metricsCalculator;
		this.validators = validators;
	}
	
	@Override
	public Iterable<IValidationEntry> validate(Iterable<Diagram> diagrams, int tolerance) {
		List<IValidationEntry> entries = new ArrayList<IValidationEntry>();
		Metrics metrics = this.metricsCalculator.calculateMetrics(diagrams);
		
		for (Diagram diagram : diagrams) {
			for (int i = 0; i < this.validators.length; i++) {
				IValidator validator = this.validators[i];
				for (IValidationEntry entry : validator.validate(diagram, metrics, tolerance)) {
					entries.add(entry);
				}
			}
		}
		return entries;
	}

}
