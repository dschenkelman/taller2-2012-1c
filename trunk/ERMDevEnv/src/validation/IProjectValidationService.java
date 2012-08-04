package validation;

import models.Diagram;

public interface IProjectValidationService {
    String generateGlobalReport(Iterable<Diagram> diagrams, int tolerance);

    String generateIndividualReport(Diagram diagram);
}
