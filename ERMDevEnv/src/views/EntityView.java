package views;

import controllers.IEntityController;
import models.EntityType;

import javax.swing.*;
import java.awt.*;

public class EntityView extends JFrame implements IEntityView {
    private IEntityController entityController;
    private IAttributeView attributeView;
    private static String TITLE = "Entity Creation";
    private JTextField entityName;

    public EntityView() {
        setTitle(TITLE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        Container container = this.getContentPane();
        container.setLayout(new FlowLayout());
        entityName = new JTextField();
        entityName.setBounds(100,100,100,100);
        JTextField entityNameText = new JTextField();
        entityNameText.setText("Name: ");
        entityNameText.setEditable(false);
        container.add(entityName);
        container.add(entityNameText);
        this.showView();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void setController(IEntityController entityController) {
        this.entityController = entityController;
    }

    @Override
    public void addAttributeView(IAttributeView attributeView) {
        this.attributeView = attributeView;
        attributeView.getInternalFrame();
    }

    @Override
    public void addStrongEntityView(IStrongEntityView strongEntityView) {
        /*TODO*/
    }

    @Override
    public IAttributeView getAttributeView() {
        return this.attributeView;
    }

    @Override
    public String getEntityName() {
        return this.entityName.getText();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.None;
    }

    @Override
    public void setEntityName(String name) {
        this.entityName.setText(name);
    }

    @Override
    public void showView() {
        this.setVisible(true);
    }

    static public void main(String args[]) {
        EntityView entityView = new EntityView();
        entityView.showView();
    }
}
