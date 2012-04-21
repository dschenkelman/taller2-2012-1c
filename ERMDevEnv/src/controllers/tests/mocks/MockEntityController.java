package controllers.tests.mocks;

import views.IEntityView;
import models.Entity;
import controllers.EntityCreatedListener;
import controllers.IEntityController;

public class MockEntityController implements IEntityController{

	private Entity entity;
	private int createCalls;

	public void setEntity(Entity entity)
	{
		this.entity = entity; 
	}
	
	@Override
	public Entity create() {
		this.createCalls++;
		return entity;
	}

	public int getCreateCallsCount() {
		return createCalls;
	}

	@Override
	public boolean addEntity() {
		return false;
	}

	@Override
	public void addSubscriber(EntityCreatedListener listener) {
		
	}

	@Override
	public void setEntityView(IEntityView entityView) {
		
	}

	@Override
	public boolean validateEntityName(String name) {
		return false;
	}
	
}
