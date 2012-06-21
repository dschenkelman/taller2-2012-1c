package controllers;

import controllers.factories.IAttributeControllerFactory;
import controllers.factories.IKeysControllerFactory;
import controllers.listeners.IEntityEventListener;
import controllers.listeners.IIdGroupEventListener;
import models.*;
import views.IEntityView;

import infrastructure.IProjectContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EntityController extends BaseController implements IEntityController {

    private Iterable<Entity> entityCollection;
    private IEntityView entityView;
    private Entity pendingEntity;
    private List<IEntityEventListener> listeners;
    private IKeysControllerFactory keysControllerFactory;
    private IAttributeControllerFactory attributeControllerFactory;
    private IAttributeController attributeController;

    public EntityController(IProjectContext projectContext, Entity entity, IEntityView entityView, IAttributeControllerFactory attributeControllerFactory, IKeysControllerFactory keysControllerFactory) {
        super(projectContext);
        this.pendingEntity = entity;
        this.attributeControllerFactory = attributeControllerFactory;
        this.keysControllerFactory = keysControllerFactory;
        this.listeners = new ArrayList<IEntityEventListener>();
        this.entityCollection = projectContext.getAllEntities(this.pendingEntity);
        this.setEntityView(entityView);
    }


    @Override
    public void create() {
        attributeController = this.attributeControllerFactory.create(this.pendingEntity.getAttributes());
        this.entityView.addAttributeView(attributeController.getAttributeView());
        this.entityView.showView();
    }

    @Override
    public void addSubscriber(IEntityEventListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public boolean validateEntityName(String name) {
        if (name.equals("")) {
            return false;
        }
        Iterator<Entity> iterator = this.entityCollection.iterator();

        boolean isRepeated = false;

        while (iterator.hasNext() && !isRepeated) {
            isRepeated = iterator.next().getName().equals(name);
        }

        return !isRepeated;
    }

    @Override
    public boolean addEntity() {

        String entityName = this.entityView.getEntityName();
        if (!this.validateEntityName(entityName)) {
            return false;
        }
        this.pendingEntity.setName(entityName);
        pendingEntity.setType(this.entityView.getEntityType());


        AttributeCollection attributeCollection = new AttributeCollection();
        for (Attribute attribute : this.attributeController.getAttributes()) {
            try {
                attributeCollection.addAttribute(attribute);
            } catch (Exception e) {
                //When editing an entity
                e.printStackTrace();
            }
        }

        this.pendingEntity.setAttributes(attributeCollection);

        for (IEntityEventListener listener : this.listeners) {
            listener.handleCreatedEvent(pendingEntity);
        }

        return true;

    }

    @Override
    public void setEntityView(IEntityView entityView) {
        this.entityView = entityView;
        this.entityView.setEntityName(this.pendingEntity.getName());
        this.entityView.setController(this);
    }

    @Override
    public void selectKeys() {
        List<IKey> possibleKeys = new ArrayList<IKey>();
        for (Attribute attribute : this.attributeController.getAttributes()) {
            possibleKeys.add(attribute);
        }
        if (possibleKeys.size() > 0) {
            IKeysController keysController = keysControllerFactory.create(possibleKeys);
            keysController.create();
        }

    }
}
