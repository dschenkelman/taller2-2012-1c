package controllers.tests.mocks;

import models.Entity;
import controllers.IEntityController;
import controllers.factories.IEntityControllerFactory;

public class MockEntityControllerFactory 
	implements IEntityControllerFactory 
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
