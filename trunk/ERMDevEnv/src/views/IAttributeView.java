package views;

import controllers.IAttributeController;
import models.Attribute;

import java.util.List;

public interface IAttributeView {

    public void setController(IAttributeController attributeController);

    public void setAttributes(List<Attribute> attributes);

    public Object getInternalFrame();
}
