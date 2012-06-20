package controllers.factories.mock;

import java.util.List;

import models.Relationship;
import models.RelationshipEntity;
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
	public IRelationshipEntityController create(
			List<RelationshipEntity> relationshipEntities) {
		// TODO Auto-generated method stub
		return null;
	}

}
