package controllers.tests.mocks;

import controllers.IAttributeController;
import models.Attribute;
import views.IAttributeView;

import java.util.List;

public class MockAttributeView implements IAttributeView {
    private IAttributeController controller;

    @Override
    public void setController(IAttributeController attributeController) {
        this.controller = attributeController;
    }

    @Override
    public void setAttributes(List<Attribute> attributes) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getInternalFrame() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IAttributeController getController() {
        return this.controller;
    }
}
