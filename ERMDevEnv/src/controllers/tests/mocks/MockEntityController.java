package controllers.tests.mocks;

import views.IEntityView;
import models.Entity;
import controllers.IEntityCreatedListener;
import controllers.IEntityController;

public class MockEntityController implements IEntityController{

	private int createCalls;
	
	@Override
	public void create() {
		this.createCalls++;
	}

	public int getCreateCallsCount() {
		return createCalls;
	}

	@Override
	public boolean addEntity() {
		return false;
	}

	@Override
	public void addSubscriber(IEntityCreatedListener listener) {
		
	}

	@Override
	public void setEntityView(IEntityView entityView) {
		
	}

	@Override
	public boolean validateEntityName(String name) {
		return false;
	}
	
}
