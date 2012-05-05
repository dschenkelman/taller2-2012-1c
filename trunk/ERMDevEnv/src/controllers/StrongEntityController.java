package controllers;

import models.IStrongEntity;
import views.IStrongEntityView;

import java.util.List;

public class StrongEntityController implements IStrongEntityController {
    private List<IStrongEntity> strongEntities;
    private IStrongEntityView strongEntitiesView;
    private IProjectContext projectContext;

    public StrongEntityController(IProjectContext projectContext, IStrongEntityView strongEntityView, List<IStrongEntity> strongEntities) {
        this.strongEntities = strongEntities;
        this.projectContext = projectContext;
        this.setStrongEntityVIew(strongEntityView);
    }

    public void setStrongEntityVIew(IStrongEntityView strongEntityView) {
        this.strongEntitiesView = strongEntityView;
        this.strongEntitiesView.setController(this);
        this.strongEntitiesView.setPossibleStrongEntities(this.projectContext.getPossibleStrongEntities(this.strongEntities));
        this.strongEntitiesView.setStrongEntities(this.strongEntities);
    }

    @Override
    public IStrongEntityView getStrongEntityView() {
        return this.strongEntitiesView;
    }

    @Override
    public Iterable<IStrongEntity> getStrongEntities() {
        return this.strongEntities;
    }

    @Override
    public void addStrongEntity(IStrongEntity strongEntity) {
        this.strongEntities.add(strongEntity);
    }
}
