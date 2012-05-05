package views;

import controllers.IEntityController;
import models.EntityType;

import javax.swing.*;

public class EntityView extends JFrame implements IEntityView {
    private IEntityController entityController;
    private IAttributeView attributeView;
    private static String TITLE = "Entity Creation";

    public EntityView() {
        setTitle(TITLE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void setController(IEntityController entityController) {
        this.entityController = entityController;
    }

    @Override
    public void addAttributeView(IAttributeView attributeView) {
        this.attributeView = attributeView;
        ((AttributeView)attributeView).getInternalFrame();
    }

    @Override
    public void addStrongEntityView(IStrongEntityView strongEntityView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IAttributeView getAttributeView() {
        return this.attributeView;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    @Override
    public EntityType getType() {
        return null;
    }

    @Override
    public void setEntityName(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void showView() {
        this.setVisible(true);
    }
}
