package validation;

import models.Diagram;

public interface IValidator {
    public Iterable<IValidationEntry> validate(Diagram diagram, Metrics metrics, int tolerance);
}
