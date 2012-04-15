package controllers.tests.mocks;

import models.Entity;
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
	
}
