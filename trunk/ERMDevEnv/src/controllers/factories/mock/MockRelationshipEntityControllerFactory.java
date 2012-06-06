package controllers.factories.mock;

import controllers.factories.IRelationshipEntityControllerFactory;
import controllers.tests.mocks.MockRelationshipEntityController;

public class MockRelationshipEntityControllerFactory implements
		IRelationshipEntityControllerFactory {

	private MockRelationshipEntityController mockRelationshipEntityController;
	
	public void setRelationshipEntityController(
			MockRelationshipEntityController mockRelationshipEntityController) {
		this.mockRelationshipEntityController = mockRelationshipEntityController;
		
	}

}
