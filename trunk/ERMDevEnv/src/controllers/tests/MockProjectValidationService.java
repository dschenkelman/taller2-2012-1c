package controllers.tests;

import models.Diagram;
import validation.IProjectValidationService;
import validation.IValidationEntry;

public class MockProjectValidationService implements IProjectValidationService {

	@Override
	public String generateGlobalReport(Iterable<Diagram> diagrams,
                                       int tolerance) {
		return null;
	}

    @Override
    public String generateIndividualReport(Diagram diagram) {
        return null;
    }

}
