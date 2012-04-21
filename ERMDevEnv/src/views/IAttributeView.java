package views;

import controllers.IAttributeController;
import models.Attribute;

import java.util.HashMap;

public interface IAttributeView {

    public void setController(IAttributeController attributeController);

    Iterable<Attribute> getAttributesSelected();

}
