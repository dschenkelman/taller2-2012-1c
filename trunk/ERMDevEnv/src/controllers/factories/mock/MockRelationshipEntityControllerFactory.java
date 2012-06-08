package controllers.factories.mock;

import models.Relationship;
import controllers.IRelationshipEntityController;
import controllers.factories.IRelationshipEntityControllerFactory;
import controllers.tests.mocks.MockRelationshipController;
import controllers.tests.mocks.MockRelationshipEntityController;

public class MockRelationshipEntityControllerFactory implements
		IRelationshipEntityControllerFactory {

	private MockRelationshipEntityController mockRelationshipEntityController;
	
	public void setRelationshipEntityController(
			IRelationshipEntityController mockRelationshipEntityController2) {
		this.mockRelationshipEntityController = (MockRelationshipEntityController) mockRelationshipEntityController2;
		
	}


	@Override
	public IRelationshipEntityController create(Relationship pendingRelationship) {
		
		
		return null ;
	}

}
