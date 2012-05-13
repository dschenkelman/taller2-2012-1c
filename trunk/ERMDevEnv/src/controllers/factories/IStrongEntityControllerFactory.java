package controllers.factories;

import controllers.IStrongEntityController;
import models.StrongEntityCollection;

public interface IStrongEntityControllerFactory {
    public IStrongEntityController create(StrongEntityCollection strongEntities);
}
