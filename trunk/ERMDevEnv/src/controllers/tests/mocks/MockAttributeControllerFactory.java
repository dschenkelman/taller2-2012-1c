package controllers.tests.mocks;

import controllers.IAttributeController;
import controllers.factories.IAttributeControllerFactory;

public class MockAttributeControllerFactory implements IAttributeControllerFactory{
    private IAttributeController attributeController;

    @Override
    public IAttributeController create() {
    	return this.attributeController;
    }

    public void setAttributeController(IAttributeController attributeController) {
        this.attributeController = attributeController;
    }
}
