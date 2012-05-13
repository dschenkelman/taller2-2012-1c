package controllers.tests.mocks;

import controllers.AttributeController;
import controllers.IAttributeController;
import controllers.factories.IAttributeControllerFactory;
import models.AttributeCollection;

public class MockAttributeControllerFactory implements IAttributeControllerFactory{
    private IAttributeController attributeController;

    @Override
    public IAttributeController create(AttributeCollection possibleAttributes) {
        return this.attributeController;
    }

    public void setAttributeController(IAttributeController attributeController) {
        this.attributeController = attributeController;
    }
}
