package controllers;

import java.util.List;

import models.Attribute;
import models.AttributeCollection;
import models.AttributeType;
import models.Cardinality;
import views.IAttributeView;

import javax.swing.*;

public interface IAttributeController{

    public Attribute addNewAttribute();

    public Iterable<Attribute> getAttributes();

    public IAttributeView getAttributeView();

    public void setAttributeView(IAttributeView attributeView);

    public Attribute addNewAttributeToAttribute(Attribute attributeSelected);

    public boolean editAttribute(Attribute attributeSelected);

    public void removeAttribute(Attribute attribute);

	void setAttributes(List<Attribute> attributes);
}
