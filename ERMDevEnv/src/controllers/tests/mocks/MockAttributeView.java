package controllers.tests.mocks;

import controllers.IAttributeController;
import views.IAttributeView;

public class MockAttributeView implements IAttributeView {
    private IAttributeController controller;

    @Override
    public void setController(IAttributeController attributeController) {
        this.controller = attributeController;
    }

    public IAttributeController getController() {
        return this.controller;
    }
}
