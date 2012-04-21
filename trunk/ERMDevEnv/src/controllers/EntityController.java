package controllers;

import models.Attribute;
import models.AttributeCollection;
import models.Entity;
import models.EntityCollection;
import views.IEntityView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityController extends BaseController implements IEntityController {

    private EntityCollection entityCollection;
    private IEntityView entityView;
    private Entity pendingEntity;
    private List<IEventListener<Entity>> listeners;
    private IAttributeController attributeController;

    public EntityController(IProjectContext projectContext, IEntityView entityView, IAttributeController attributeController) {
        super(projectContext);
        this.entityCollection = projectContext.getEntityCollection();
        this.attributeController = attributeController;
        this.listeners = new ArrayList<IEventListener<Entity>>();
        this.entityView = entityView;
        this.entityView.setController(this);
        this.pendingEntity = new Entity("");
    }


    @Override
    public void create() {
        this.entityView.addAttributeView(this.attributeController.getAttributeView());
        this.entityView.showView();
    }

    @Override
    public void addSubscriber(IEventListener<Entity> listener) {
        this.listeners.add(listener);
    }

    @Override
    public boolean validateEntityName(String name) {
        if (name.equals("")) {
            return false;
        }
        Iterator<Entity> iterator = this.entityCollection.iterator();

        boolean isRepeated = false;
        String entityName = this.entityView.getEntityName();

        while (iterator.hasNext() && !isRepeated) {
            isRepeated = iterator.next().getName().equals(entityName);
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
        for (Attribute attribute : this.attributeController.getAttributesSelected()) {
            try {
                attributeCollection.addAttribute(attribute);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (IEventListener<Entity> listener : this.listeners) {
            listener.handleEvent(pendingEntity);
        }

        return true;

    }

    @Override
    public void setEntityView(IEntityView entityView) {
        this.entityView = entityView;
    }

}
