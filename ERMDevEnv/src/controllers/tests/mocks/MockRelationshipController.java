package controllers.tests.mocks;

import controllers.IRelationshipController;

public class MockRelationshipController implements IRelationshipController{

	private boolean createCalled;
	
	public boolean createWasCalled() {
		return this.createCalled;
	}

	@Override
	public void create() {
		this.createCalled = true;
	}

}
