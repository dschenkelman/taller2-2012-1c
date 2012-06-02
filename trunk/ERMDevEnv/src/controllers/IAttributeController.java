package controllers;

import models.Attribute;
import models.AttributeType;
import models.Cardinality;
import views.IAttributeView;

public interface IAttributeController{

    public void addNewAttribute(String name, boolean isKey, Cardinality cardinality, AttributeType attributeType, String expression);

    Iterable<Attribute> getAttributes();

    IAttributeView getAttributeView();

    public void setAttributeView(IAttributeView attributeView);
}
