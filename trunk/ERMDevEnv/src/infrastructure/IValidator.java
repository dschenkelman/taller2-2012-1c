package infrastructure;

import models.Diagram;

public interface IValidator {
    public Iterable<IValidation> validate(Diagram diagram);
}
