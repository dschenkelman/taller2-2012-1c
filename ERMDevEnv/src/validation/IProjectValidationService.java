package validation;

import models.Diagram;

public interface IProjectValidationService {
	Iterable<IValidationEntry> validate(Iterable<Diagram> diagrams, int tolerance);
}
