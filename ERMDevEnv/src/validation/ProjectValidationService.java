package validation;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import models.Diagram;

public class ProjectValidationService implements IProjectValidationService {

	private IMetricsCalculator metricsCalculator;
	private IValidator[] validators;

	public ProjectValidationService(IMetricsCalculator metricsCalculator, IValidator[] validators){
		this.metricsCalculator = metricsCalculator;
		this.validators = validators;
	}
	
	@Override
	public void validate(Iterable<Diagram> diagrams, int tolerance) {
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
		
		/*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        /*  next, get the Template  */
        Template t = ve.getTemplate( "projectValidation.vm" );
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("metrics", metrics.getMetrics());
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        /* show the World */
        System.out.println( writer.toString() );   
	}

}
