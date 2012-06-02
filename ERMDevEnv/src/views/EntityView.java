/*
 * Created by JFormDesigner on Sat Jun 02 13:13:10 ART 2012
 */

package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import controllers.IEntityController;
import models.EntityType;

/**
 * @author Gaston Daniel Festa
 */
public class EntityView implements IEntityView {
    private IEntityController entityController;
    private IAttributeView attributeView;
    private static String TITLE = "Entity Creation";

    public EntityView() {
        initComponents();
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createEntity();
            }
        });
        selectKeysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                entityController.selectKeys();
            }
        });
        frame1.setTitle(TITLE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gaston Daniel Festa
        frame1 = new JFrame();
        entityNameText = new JLabel();
        entityName = new JTextField();
        entityTypeText = new JLabel();
        entityTypeComboBox = new JComboBox();
        attributePanel = new JPanel();
        selectKeysButton = new JButton();
        createButton = new JButton();

        //======== frame1 ========
        {
            frame1.setTitle("Entity Creation");
            Container frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(new FormLayout(
                "28*(default, $lcgap), default",
                "20*(default, $lgap), default"));

            //---- entityNameText ----
            entityNameText.setText("Name: ");
            entityNameText.setLabelFor(entityName);
            frame1ContentPane.add(entityNameText, CC.xy(5, 3));
            frame1ContentPane.add(entityName, CC.xywh(9, 3, 41, 1));

            //---- entityTypeText ----
            entityTypeText.setText("Type");
            entityTypeText.setLabelFor(entityTypeComboBox);
            frame1ContentPane.add(entityTypeText, CC.xy(5, 7));
            frame1ContentPane.add(entityTypeComboBox, CC.xy(9, 7));

            //======== attributePanel ========
            {

                // JFormDesigner evaluation mark
                attributePanel.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), attributePanel.getBorder())); attributePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

                attributePanel.setLayout(new FormLayout(
                    "3*(default, $lcgap), default",
                    "2*(default, $lgap), default"));
            }
            frame1ContentPane.add(attributePanel, CC.xywh(1, 15, 52, 17));

            //---- selectKeysButton ----
            selectKeysButton.setText("Select Keys");
            frame1ContentPane.add(selectKeysButton, CC.xy(47, 33));

            //---- createButton ----
            createButton.setText("Create");
            frame1ContentPane.add(createButton, CC.xy(51, 39));
            frame1.pack();
            frame1.setLocationRelativeTo(frame1.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void createEntity() {
        this.frame1.remove(this.attributePanel);
        this.entityNameText.setText("Hola");
        /*if (entityController.addEntity()) {
            this.frame1.setVisible(false);
        }*/
    }

    @Override
    public void setController(IEntityController entityController) {
        this.entityController = entityController;
    }

    @Override
    public void addAttributeView(IAttributeView attributeView) {
        this.attributeView = attributeView;
        this.attributePanel = (JPanel) attributeView.getInternalFrame();
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
        return EntityType.valueOf((String) this.entityTypeComboBox.getItemAt(this.entityTypeComboBox.getSelectedIndex()));
    }

    @Override
    public boolean isVisible() {
        return this.frame1.isVisible();
    }

    @Override
    public void setEntityName(String name) {
        this.entityName.setText(name);
    }

    @Override
    public void showView() {
        this.frame1.setVisible(true);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gaston Daniel Festa
    private JFrame frame1;
    private JLabel entityNameText;
    private JTextField entityName;
    private JLabel entityTypeText;
    private JComboBox entityTypeComboBox;
    private JPanel attributePanel;
    private JButton selectKeysButton;
    private JButton createButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String args[]) {
        (new EntityView()).showView();
    }
}
