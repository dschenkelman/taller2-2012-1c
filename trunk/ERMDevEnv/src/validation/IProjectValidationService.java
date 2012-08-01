package validation;

import models.Diagram;

public interface IProjectValidationService {
	String generateValidationReport(Iterable<Diagram> diagrams, int tolerance);
}
