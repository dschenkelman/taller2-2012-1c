package views;

import controllers.IEntityController;
import models.EntityType;

public interface IEntityView {

    public void setController(IEntityController entityController);

    public void showView();

    public void addAttributeView(IAttributeView attributeView);

    public void addStrongEntityView(IStrongEntityView strongEntityView);

    public IAttributeView getAttributeView();

    public String getEntityName();

    public EntityType getType();

    public boolean isVisible();

    public void setEntityName(String name);
}
