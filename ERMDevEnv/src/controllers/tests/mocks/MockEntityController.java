package controllers.tests.mocks;

import views.IEntityView;
import controllers.IEntityController;
import controllers.IEntityEventListener;

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
	public void setEntityView(IEntityView entityView) {
		
	}

	@Override
	public boolean validateEntityName(String name) {
		return false;
	}

	@Override
	public void addSubscriber(IEntityEventListener listener) {		
	}
	
}
