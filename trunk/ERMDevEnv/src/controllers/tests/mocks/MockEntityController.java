package controllers.tests.mocks;

import controllers.IEventListener;
import views.IEntityView;
import models.Entity;
import controllers.IEntityController;

public class MockEntityController implements IEntityController{

	private int createCalls;
	
	@Override
	public void create() {
		this.createCalls++;
	}

    @Override
    public void addSubscriber(IEventListener<Entity> listener) {
    }

    public int getCreateCallsCount() {
		return createCalls;
	}

	@Override
	public boolean addEntity() {
		return false;
	}



	@Override
	public void setEntityView(IEntityView entityView) {
		
	}

	@Override
	public boolean validateEntityName(String name) {
		return false;
	}
	
}
