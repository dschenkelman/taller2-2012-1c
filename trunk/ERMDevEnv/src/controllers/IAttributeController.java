package controllers;

import models.Attribute;
import models.AttributeType;
import models.Cardinality;
import views.IAttributeView;

import javax.swing.*;

public interface IAttributeController{

    public Attribute addNewAttribute();

    Iterable<Attribute> getAttributes();

    IAttributeView getAttributeView();

    public void setAttributeView(IAttributeView attributeView);

    void addNewAttributeToAttribute(Attribute attributeSelected);

    public void editAttribute(Attribute attributeSelected);
}
