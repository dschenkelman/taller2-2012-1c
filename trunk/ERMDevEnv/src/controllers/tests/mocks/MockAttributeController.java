package controllers.tests.mocks;

import controllers.IAttributeController;
import models.Attribute;
import views.IAttributeView;

import java.util.ArrayList;

public class MockAttributeController implements IAttributeController {
    private IAttributeView attributeView;

    @Override
    public void selectKeys() {
    }

    @Override
    public Iterable<Attribute> getAttributesSelected() {
        return new ArrayList<Attribute>();
    }

    @Override
    public IAttributeView getAttributeView() {
        return this.attributeView;
    }

    public void setAttributeView(IAttributeView attributeView){
        this.attributeView = attributeView;
    }
}
