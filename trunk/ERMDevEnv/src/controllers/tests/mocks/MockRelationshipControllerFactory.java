package controllers.tests.mocks;

import controllers.IRelationshipController;
import controllers.factories.IRelationshipControllerFactory;
import models.Relationship;

public class MockRelationshipControllerFactory 
	implements IRelationshipControllerFactory{

	private IRelationshipController controller;
	
	@Override
	public IRelationshipController create() {
		return this.controller;
	}

	@Override
	public IRelationshipController create(Relationship relationship) {
		return null;
	}

	public void setController(IRelationshipController controller) {
		this.controller = controller;
	}

	public IRelationshipController getController() {
		return controller;
	}
}
