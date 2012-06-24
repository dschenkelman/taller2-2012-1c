package controllers;

import infrastructure.Func;
import infrastructure.IProjectContext;
import infrastructure.IterableExtensions;
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
    public Attribute addNewAttribute() {
        String attName = attributeView.getName();
        if (IterableExtensions.firstOrDefault(attributes, new FuncAttrCmp(), attName) == null && !attName.equals("")) {
            String expressionClone = (attributeView.getAttributeType() == AttributeType.calculated || attributeView.getAttributeType() == AttributeType.copy) ? attributeView.getExpression() : null;
            Attribute att = new Attribute(attributeView.getName(), attributeView.getCardinality(), new IdGroupCollection(), attributeView.getAttributeType(), expressionClone);
            this.attributes.add(att);
            return att;
        }
        return null;
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

    @Override
    public void addNewAttributeToAttribute(Attribute attributeSelected) {
        if (attributeSelected != null) {
            String attName = attributeView.getName();
            if (IterableExtensions.firstOrDefault(attributeSelected.getAttributes(), new FuncAttrCmp(), attName) == null) {
                String expressionClone = (attributeView.getAttributeType() == AttributeType.calculated || attributeView.getAttributeType() == AttributeType.copy) ? attributeView.getExpression() : null;
                Attribute att = new Attribute(attName, attributeView.getCardinality(), new IdGroupCollection(), attributeView.getAttributeType(), expressionClone);
                try {
                    AttributeCollection attributeCollection = attributeSelected.getAttributes();
                    if (attributeCollection == null) {
                        attributeCollection = new AttributeCollection();
                        attributeSelected.setAttributes(attributeCollection);
                    }
                    attributeCollection.addAttribute(att);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void editAttribute(Attribute attributeSelected) {
        attributeSelected.setName(attributeView.getName());
        attributeSelected.setCardinality(attributeView.getCardinality());
        attributeSelected.setType(attributeView.getAttributeType());
        AttributeType attType = attributeView.getAttributeType();
        if (attType == AttributeType.calculated || attType == AttributeType.copy)
            attributeSelected.setExpression(attributeView.getExpression());
    }

    private class FuncAttrCmp extends Func<Attribute, String, Boolean> {
        @Override
        public Boolean execute(Attribute attribute, String s) {
            return attribute.getName().equals(s);
        }
    }
}