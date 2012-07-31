package validation;

import models.Diagram;

public interface IProjectValidationService {
	void validate(Iterable<Diagram> diagrams, int tolerance);
}
