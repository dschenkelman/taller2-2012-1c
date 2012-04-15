package controllers.tests.mocks;

import models.Entity;
import controllers.IEntityController;

public class MockEntityController implements IEntityController{

	private Entity entity;

	public void setEntity(Entity entity)
	{
		this.entity = entity; 
	}
	
	@Override
	public Entity create() {
		return entity;
	}
	
}
