package controllers;

import models.Entity;
import models.IStrongEntity;
import views.IStrongEntityView;

import java.util.List;

public interface IStrongEntityController {
    public IStrongEntityView getStrongEntityView();

    public Iterable<IStrongEntity> getStrongEntities();

    public void addStrongEntity(IStrongEntity strongEntity);

    public void setStrongEntityVIew(IStrongEntityView strongEntityView);

}
