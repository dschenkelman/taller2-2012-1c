package controllers.tests.mocks;

import controllers.IEntityController;
import models.EntityType;
import views.IAttributeView;
import views.IEntityView;
import views.IStrongEntityView;

public class MockEntityView implements IEntityView {
    private IEntityController controller;
    public boolean addViewWasCall = false;
    private boolean showViewWasCall = false;
    private String name;

    @Override
    public void setController(IEntityController entityController) {
        this.controller = entityController;
    }

    @Override
    public void showView() {
        this.showViewWasCall = true;
    }

    @Override
    public void addAttributeView(IAttributeView attributeView) {
        this.addViewWasCall = true;
    }

    @Override
    public IAttributeView getAttributeView() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEntityName() {
        return this.name;
    }

    @Override
    public EntityType getEntityType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isVisible() {
        return this.showViewWasCall;
    }

    public IEntityController getController() {
        return controller;
    }

    public void setEntityName(String s) {
        this.name = s;
    }
    
    public String getName(){
        return this.name;
    }
}
