package controllers.tests.mocks;

import controllers.IAttributeController;
import models.Attribute;
import views.IAttributeView;

public class MockAttributeView implements IAttributeView {
    private IAttributeController controller;
    private Iterable<Attribute> attribute;

    @Override
    public void setController(IAttributeController attributeController) {
        this.controller = attributeController;
    }

    @Override
    public Iterable<Attribute> getAttributesSelected() {
        return attribute;
    }

    public void setAttribute(Iterable<Attribute> attribute){
        this.attribute = attribute;
    }

    public IAttributeController getController(){
        return this.controller;
    }
}
