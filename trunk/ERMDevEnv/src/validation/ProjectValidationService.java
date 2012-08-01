package validation;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.NumberTool;

import models.Diagram;

public class ProjectValidationService implements IProjectValidationService {

	private IMetricsCalculator metricsCalculator;
	private IValidator[] validators;

	public ProjectValidationService(IMetricsCalculator metricsCalculator, IValidator[] validators){
		this.metricsCalculator = metricsCalculator;
		this.validators = validators;
	}
	
	@Override
	public String generateValidationReport(Iterable<Diagram> diagrams, int tolerance) {
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
		
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        Template template = engine.getTemplate("projectValidation.vm");
        VelocityContext context = new VelocityContext();
        context.put("metrics", metrics.getMetrics());
        context.put("entries", entries);
        context.put("numberTool", new NumberTool());
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();   
	}

}
