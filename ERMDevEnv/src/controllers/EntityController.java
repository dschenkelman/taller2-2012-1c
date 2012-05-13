package controllers;

import controllers.factories.IAttributeControllerFactory;
import controllers.factories.IStrongEntityControllerFactory;
import models.*;
import views.IEntityView;

import infrastructure.IProjectContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityController extends BaseController implements IEntityController {

    private Iterable<Entity> entityCollection;
    private IEntityView entityView;
    private Entity pendingEntity;
    private List<IEntityEventListener> listeners;
    private IAttributeControllerFactory attributeControllerFactory;
    private IStrongEntityControllerFactory strongEntityControllerFactory;
    private IAttributeController attributeController;
    private IStrongEntityController strongEntityController;

    public EntityController(IProjectContext projectContext, Entity entity, IEntityView entityView, IAttributeControllerFactory attributeControllerFactory, IStrongEntityControllerFactory strongEntityControllerFactory) {
        super(projectContext);
        this.pendingEntity = entity;
        this.attributeControllerFactory= attributeControllerFactory;
        this.strongEntityControllerFactory = strongEntityControllerFactory;
        this.listeners = new ArrayList<IEntityEventListener>();
        this.entityCollection = projectContext.getEntityCollection(this.pendingEntity);
        this.setEntityView(entityView);
    }


    @Override
    public void create() {
        attributeController = this.attributeControllerFactory.create(this.pendingEntity.getAttributes());
        strongEntityController = this.strongEntityControllerFactory.create(this.pendingEntity.getStrongEntities());
        this.entityView.addStrongEntityView(strongEntityController.getStrongEntityView());
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
        pendingEntity.setType(this.entityView.getType());

        AttributeCollection attributeCollection = this.pendingEntity.getAttributes();
        for (Attribute attribute : this.attributeController.getAttributes()) {
            try {
                attributeCollection.addAttribute(attribute);
            } catch (Exception e) {
                //When editing an entity
                e.printStackTrace();
            }
        }

        StrongEntityCollection strongEntityCollection = this.pendingEntity.getStrongEntities();
        for (IStrongEntity strongEntity : this.strongEntityController.getStrongEntities()) {
            try {
                strongEntityCollection.addStrongEntity(strongEntity);
            } catch (Exception e) {
                //When editing an entity
                e.printStackTrace();
            }
        }

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

}
