package controllers;

import infrastructure.IControllerFactory;
import models.*;
import views.IAttributeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttributeController extends BaseController implements IAttributeController, IEventListener {

    private IAttributeView attributeView;
    private IControllerFactory<IKeysController, Iterable<IKey>> keysControllerFactory;
    private List<Attribute> attributes;

    public AttributeController(IProjectContext projectContext, IAttributeView attributeView, IControllerFactory<IKeysController, Iterable<IKey>> keysControllerFactory) {
        super(projectContext);
        this.keysControllerFactory = keysControllerFactory;
        this.attributes = new ArrayList<Attribute>();
        this.attributeView = attributeView;
        this.attributeView.setController(this);
    }

    @Override
    public void selectKeys() {
        List<IKey> possibleKeys = new ArrayList<IKey>();
        for (Attribute attribute : this.getAttributesSelected()) {
            possibleKeys.add(attribute);
        }
        IKeysController keysController = keysControllerFactory.create(possibleKeys);
        keysController.create();
        keysController.addSubscriber(this);
    }

    @Override
    public Iterable<INameable> getPossibleAttributes() {
        return this.projectContext.getPossibleAttributes();
    }

    @Override
    public void addAttribute(INameable attribute) {
        this.attributes.add((Attribute) attribute);
    }

    @Override
    public void addNewAttribute(String name, boolean isKey, Cardinality cardinality, AttributeType attributeType, String expression) {
        this.attributes.add(new Attribute(name, isKey, cardinality, cardinality, new IdGroupCollection(), attributeType, expression));
    }

    @Override
    public Iterable<Attribute> getAttributesSelected() {
        return attributes;
    }

    @Override
    public IAttributeView getAttributeView() {
        return this.attributeView;
    }

    @Override
    public void handleEvent(Object... object) {
        HashMap<Integer, List<IKey>> keys = (HashMap<Integer, List<IKey>>) object[0];
        for (Integer idGroup : keys.keySet()) {
            for (IKey key : keys.get(idGroup)) {
                try {
                    ((Attribute) key).getIdGroup().addIdGroup(idGroup);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}