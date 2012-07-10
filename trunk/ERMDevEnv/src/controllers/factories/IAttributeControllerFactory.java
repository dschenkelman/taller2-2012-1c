package controllers.factories;

import controllers.IAttributeController;
import models.AttributeCollection;

public interface IAttributeControllerFactory {
    public IAttributeController create();
}
