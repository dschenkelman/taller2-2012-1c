package controllers.tests.mocks;

import controllers.IRelationshipController;
import models.Relationship;
import infrastructure.IControllerFactory;

public class MockRelationshipControllerFactory 
	implements IControllerFactory<IRelationshipController, Relationship>{

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
