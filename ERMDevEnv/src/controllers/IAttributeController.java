package controllers;

import models.Attribute;
import models.AttributeType;
import models.Cardinality;
import models.INameable;
import views.IAttributeView;

public interface IAttributeController {

    public void selectKeys();

    public Iterable<INameable> getPossibleAttributes();

    public void addAttribute(INameable attribute);

    public void addNewAttribute(String name, boolean isKey, Cardinality cardinality, AttributeType attributeType, String expression);

    Iterable<Attribute> getAttributesSelected();

    IAttributeView getAttributeView();
}
