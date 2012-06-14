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

    public MockAttributeController(){
        this.attributes = new ArrayList<Attribute>();
    }

    @Override
    public Attribute addNewAttribute(String name, boolean isKey, Cardinality cardinality, AttributeType attributeType, String expression) {
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

    public void setAttributeView(IAttributeView attributeView){
        this.attributeView = attributeView;
    }

    @Override
    public void addNewAttributeToAttribute(String nameText, boolean isKey, Cardinality cardinality, AttributeType attType, String expression, Attribute attributeSelected) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAttributes(List<Attribute> attributes){
        this.attributes = attributes;
    }
}
