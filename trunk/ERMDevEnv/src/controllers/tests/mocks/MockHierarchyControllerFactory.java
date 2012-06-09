package controllers.tests.mocks;

import models.Hierarchy;
import controllers.IHierarchyController;
import controllers.factories.IHierarchyControllerFactory;

public class MockHierarchyControllerFactory implements IHierarchyControllerFactory {

	private IHierarchyController controller;
	private int createCalls;

	public void setController(IHierarchyController hierarchyController) {
		this.controller = hierarchyController;
	}

	@Override
	public IHierarchyController create() {
		this.createCalls++;
		return this.controller;
	}

	@Override
	public IHierarchyController create(Hierarchy hierarchy) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCreateCallsCount() {
		return this.createCalls;
	}

}
