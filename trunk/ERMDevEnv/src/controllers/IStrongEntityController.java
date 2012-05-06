package controllers;

import models.IStrongEntity;
import views.IStrongEntityView;

public interface IStrongEntityController {
    public IStrongEntityView getStrongEntityView();

    public Iterable<IStrongEntity> getStrongEntities();

    public void addStrongEntity(IStrongEntity strongEntity);

    public void setStrongEntityVIew(IStrongEntityView strongEntityView);

}
