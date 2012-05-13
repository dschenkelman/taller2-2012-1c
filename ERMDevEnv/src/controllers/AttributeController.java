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
    private IKeysControllerFactory keysControllerFactory;
    private List<Attribute> attributes;

    public AttributeController(IProjectContext projectContext, List<Attribute> attributes, 
    		IAttributeView attributeView, IKeysControllerFactory keysControllerFactory) {
        super(projectContext);
        this.keysControllerFactory = keysControllerFactory;
        this.attributes = attributes;
        this.setAttributeView(attributeView);
    }

    @Override
    public void selectKeys() {
        List<IKey> possibleKeys = new ArrayList<IKey>();
        for (Attribute attribute : this.getAttributes()) {
            possibleKeys.add(attribute);
        }
        IKeysController keysController = keysControllerFactory.create(possibleKeys);
        keysController.addSubscriber(this);
        keysController.create();
    }

    @Override
    public void addNewAttribute(String name, boolean isKey, Cardinality cardinality, AttributeType attributeType, String expression) {
        this.attributes.add(new Attribute(name, isKey, cardinality, new IdGroupCollection(), attributeType, expression));
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
    public void handleEvent(HashMap<Integer, List<IKey>> keys) {
        for (Integer idGroup : keys.keySet()) {
            for (IKey key : keys.get(idGroup)) {
                try {
                    key.getIdGroup().addIdGroup(idGroup);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}