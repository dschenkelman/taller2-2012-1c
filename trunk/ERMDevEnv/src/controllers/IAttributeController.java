package controllers;

import models.Attribute;
import models.AttributeType;
import models.Cardinality;
import views.IAttributeView;

import javax.swing.*;

public interface IAttributeController{

    public Attribute addNewAttribute(String name, boolean isKey, Cardinality cardinality, AttributeType attributeType, String expression);

    Iterable<Attribute> getAttributes();

    IAttributeView getAttributeView();

    public void setAttributeView(IAttributeView attributeView);

    void addNewAttributeToAttribute(String nameText, boolean isKey, Cardinality cardinality , AttributeType attType, String expression, Attribute attributeSelected);
}
