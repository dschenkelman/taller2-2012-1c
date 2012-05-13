package controllers.factories;

import controllers.IEntityController;
import models.Entity;

public interface IEntityControllerFactory {
	IEntityController create();
	IEntityController create(Entity entity);
}
