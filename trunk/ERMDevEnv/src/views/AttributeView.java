package views;

import controllers.IAttributeController;
import models.Attribute;

import java.util.List;

public class AttributeView implements IAttributeView {
    private IAttributeController controller;
    private Object internalFrame;

    @Override
    public void setController(IAttributeController attributeController) {
        this.controller = attributeController;
    }

    @Override
    public void setAttributes(List<Attribute> attributes) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getInternalFrame() {
        return internalFrame;
    }
}
