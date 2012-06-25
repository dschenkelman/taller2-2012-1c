package controllers.tests.mocks;

import controllers.IAttributeController;
import models.Attribute;
import models.AttributeType;
import models.Cardinality;
import models.IKey;
import views.IAttributeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockAttributeController implements IAttributeController {
    private IAttributeView attributeView;
    private List<Attribute> attributes;

    public MockAttributeController() {
        this.attributes = new ArrayList<Attribute>();
    }

    @Override
    public Attribute addNewAttribute() {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }

    @Override
    public Iterable<Attribute> getAttributes() {
        return this.attributes;
    }

    @Override
    public IAttributeView getAttributeView() {
        return this.attributeView;
    }

    public void setAttributeView(IAttributeView attributeView) {
        this.attributeView = attributeView;
    }

    @Override
    public Attribute addNewAttributeToAttribute(Attribute attributeSelected) {
        return null;
    }

    @Override
    public void editAttribute(Attribute attributeSelected) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeAttribute(Attribute attribute) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
