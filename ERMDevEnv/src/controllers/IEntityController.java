package controllers;

import models.Entity;
import views.IEntityView;

public interface IEntityController {

    public Entity create(); //should be public void create()

    public void addSubscriber(EntityCreatedListener listener);

    public boolean addEntity();

    public boolean validateEntityName(String name);

    public void setEntityView(IEntityView entityView);
}
