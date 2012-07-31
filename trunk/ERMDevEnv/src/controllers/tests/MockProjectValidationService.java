package controllers.tests;

import models.Diagram;
import validation.IProjectValidationService;
import validation.IValidationEntry;

public class MockProjectValidationService implements IProjectValidationService {

	@Override
	public Iterable<IValidationEntry> validate(Iterable<Diagram> diagrams,
			int tolerance) {
		return null;
	}

}
