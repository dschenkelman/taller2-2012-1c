package controllers.tests.mocks;

import infrastructure.IControllerFactory;
import models.Entity;
import controllers.IEntityController;

public class MockEntityControllerFactory implements IControllerFactory<IEntityController, Entity> 
{
		private IEntityController controller;
		
		public void setController(IEntityController controller)
		{
			this.controller = controller;
		}
		
		@Override
		public IEntityController create() {
			return this.controller;
		}
}
