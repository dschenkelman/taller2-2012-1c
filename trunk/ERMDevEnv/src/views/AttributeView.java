/*
 * Created by JFormDesigner on Fri Jun 08 17:55:10 ART 2012
 */

package views;

import javax.swing.*;

import com.jgoodies.forms.factories.*;

import com.jgoodies.forms.layout.*;
import controllers.IAttributeController;
import models.Attribute;
import models.AttributeType;
import models.EntityType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Gaston Daniel Festa
 */
public class AttributeView implements IAttributeView {
    private IAttributeController controller;
    private DefaultListModel defaultListModel;
    private List<Attribute> attributeModelList;

    public AttributeView() {
        initComponents();
        defaultListModel = new DefaultListModel();
        attributeList.setModel(defaultListModel);
        createAttributeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createAttribute();
            }
        });
    }

    private void createAttribute() {
        if (!name.getText().equals("")) {
            AttributeType type = AttributeType.valueOf((String) this.type.getItemAt(this.type.getSelectedIndex()));
            this.controller.addNewAttribute(name.getText(), false, null, type, this.expression.getText());
            this.name.setText("");
            this.expression.setText("");
            this.type.setSelectedIndex(0);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gaston Daniel Festa
        panel1 = new JPanel();
        attributesText = new JLabel();
        scrollPane1 = new JScrollPane();
        attributeList = new JList();
        nameText = new JLabel();
        name = new JTextField();
        typeText = new JLabel();
        type = new JComboBox(AttributeType.attributesTypes);
        expressionText = new JLabel();
        scrollPane2 = new JScrollPane();
        expression = new JTextArea();
        createAttributeButton = new JButton();

        //======== panel1 ========
        {

            // JFormDesigner evaluation mark
            panel1.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                            java.awt.Color.red), panel1.getBorder()));
            panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if ("border".equals(e.getPropertyName())) throw new RuntimeException();
                }
            });

            panel1.setLayout(new FormLayout(
                    "19*(default, $lcgap), default",
                    "19*(default, $lgap), default"));

            //---- attributesText ----
            attributesText.setText("Attributes");
            panel1.add(attributesText, CC.xy(5, 1));

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(attributeList);
            }
            panel1.add(scrollPane1, CC.xywh(3, 3, 20, 21));

            //---- nameText ----
            nameText.setText("Name");
            panel1.add(nameText, CC.xy(27, 3));
            panel1.add(name, CC.xywh(31, 3, 5, 1));

            //---- typeText ----
            typeText.setText("Type");
            panel1.add(typeText, CC.xy(27, 5));
            panel1.add(type, CC.xy(31, 5));

            //---- expressionText ----
            expressionText.setText("Expression");
            panel1.add(expressionText, CC.xy(27, 7));

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(expression);
            }
            panel1.add(scrollPane2, CC.xywh(31, 7, 6, 6));

            //---- createAttributeButton ----
            createAttributeButton.setText("Create Attribute");
            panel1.add(createAttributeButton, CC.xy(31, 19));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gaston Daniel Festa
    private JPanel panel1;
    private JLabel attributesText;
    private JScrollPane scrollPane1;
    private JList attributeList;
    private JLabel nameText;
    private JTextField name;
    private JLabel typeText;
    private JComboBox type;
    private JLabel expressionText;
    private JScrollPane scrollPane2;
    private JTextArea expression;
    private JButton createAttributeButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    @Override
    public void setController(IAttributeController attributeController) {
        this.controller = attributeController;
    }

    @Override
    public void setAttributes(List<Attribute> attributes) {
        this.attributeModelList = attributes;
        for (Attribute attribute : this.attributeModelList) {
            defaultListModel.addElement(attribute.getName());
        }
    }

    @Override
    public Object getInternalFrame() {
        return panel1;
    }
}
