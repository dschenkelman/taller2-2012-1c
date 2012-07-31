package controllers.tests;

import models.Diagram;
import validation.IProjectValidationService;
import validation.IValidationEntry;

public class MockProjectValidationService implements IProjectValidationService {

	@Override
	public void validate(Iterable<Diagram> diagrams,
			int tolerance) {
	}

}
