package controllers;

import models.Attribute;
import views.IAttributeView;

public interface IAttributeController{

    public void selectKeys();

    Iterable<Attribute> getAttributesSelected();

    IAttributeView getAttributeView();
}
