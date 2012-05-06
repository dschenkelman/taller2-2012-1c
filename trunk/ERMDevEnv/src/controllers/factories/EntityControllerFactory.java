package controllers.factories;

import models.Entity;
import controllers.IEntityController;

public class EntityControllerFactory implements IEntityControllerFactory {

	@Override
	public IEntityController create() {
		return null;
	}

	@Override
	public IEntityController create(Entity relationship) {
		return null;
	}

}
