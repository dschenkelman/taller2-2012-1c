package controllers;

import views.IEntityView;

public interface IEntityController {

    public void create();

    public void addSubscriber(IEntityCreatedListener listener);

    public boolean addEntity();

    public boolean validateEntityName(String name);

    public void setEntityView(IEntityView entityView);
}
