package controllers;

import infrastructure.IProjectContext;
import models.*;
import views.IAttributeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controllers.factories.IKeysControllerFactory;

public class AttributeController extends BaseController implements IAttributeController {

    private IAttributeView attributeView;
    private List<Attribute> attributes;

    public AttributeController(IProjectContext projectContext, List<Attribute> attributes, IAttributeView attributeView) {
        super(projectContext);
        this.attributes = attributes;
        this.setAttributeView(attributeView);
    }

    @Override
    public void addNewAttribute(String name, boolean isKey, Cardinality cardinality, AttributeType attributeType, String expression) {
        String expressionClone = (attributeType == AttributeType.calculated || attributeType == AttributeType.copy) ? expression : null;
        this.attributes.add(new Attribute(name, isKey, cardinality, new IdGroupCollection(), attributeType, expressionClone));
    }

    @Override
    public Iterable<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public IAttributeView getAttributeView() {
        return this.attributeView;
    }

    @Override
    public void setAttributeView(IAttributeView attributeView) {
        this.attributeView = attributeView;
        this.attributeView.setController(this);
        this.attributeView.setAttributes(this.attributes);
    }
}