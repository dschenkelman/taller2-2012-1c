package views;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.factories.*;

import com.jgoodies.forms.layout.*;
import controllers.IAttributeController;
import models.Attribute;
import models.AttributeCollection;
import models.AttributeType;
import models.Cardinality;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AttributeView implements IAttributeView {

    public AttributeView() {
        initComponents();
        defaultListModel = new DefaultListModel();
        attributeList.setModel(defaultListModel);
        this.attributeSelected = null;
        attributeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                attributeSelected((Attribute) attributeList.getSelectedValue());
                attributeSelected();
            }
        });
        createAttributeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createAttribute();
            }
        });
        addToAttibuteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addAttributeToSelectedOne();
            }
        });
        internalAttributesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                attributeSelected((Attribute) internalAttributesList.getSelectedValue());
            }
        });
        editAttributeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAttribute();
            }
        });
    }

    @Override
    public void setController(IAttributeController attributeController) {
        this.controller = attributeController;
    }

    @Override
    public void setAttributes(List<Attribute> attributes) {
        this.attributeModelList = attributes;
        for (Attribute attribute : this.attributeModelList) {
            defaultListModel.addElement(attribute);
        }
    }

    @Override
    public Object getInternalFrame() {
        return panel1;
    }

    @Override
    public String getName() {
        return name.getText();
    }

    @Override
    public boolean isKey() {
        //TODO
        return false;
    }

    @Override
    public Cardinality getCardinality() {
        //TODO
        return null;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.valueOf((String) type.getItemAt(type.getSelectedIndex()));
    }

    @Override
    public String getExpression() {
        return this.expression.getText();
    }

    private void attributeSelected() {
        if (attributeSelected != null) {
            AttributeCollection attributeCollection = attributeSelected.getAttributes();
            if (attributeCollection != null) {
                DefaultListModel internalListModel = new DefaultListModel();
                for (Attribute attribute : attributeCollection) {
                    internalListModel.addElement(attribute);
                }
                this.internalAttributesList.setModel(internalListModel);
            } else {
                this.internalAttributesList.setModel(new DefaultListModel());
            }
        }
    }

    private void addAttributeToSelectedOne() {
        if (attributeSelected != null) {
            controller.addNewAttributeToAttribute(attributeSelected);
            cleanView();
            attributeSelected();
        }
    }

    private void createAttribute() {
        if (!name.getText().equals("")) {
            Attribute attribute = this.controller.addNewAttribute();
            cleanView();
            defaultListModel.addElement(attribute);
        }
    }

    private void cleanView() {
        this.name.setText("");
        this.expression.setText("");
        this.type.setSelectedIndex(0);
        internalAttributesList.updateUI();
        internalAttributesList.clearSelection();
        attributeList.clearSelection();
        attributeList.updateUI();
    }

    private void attributeSelected(Attribute selectedValue) {
        if (selectedValue != null) {
            this.attributeSelected = selectedValue;
            this.name.setText(attributeSelected.getName());
            String expressionClone = (attributeSelected.getType() == AttributeType.calculated || attributeSelected.getType() == AttributeType.copy) ? attributeSelected.getExpression() : "";
            this.expression.setText(expressionClone);
            this.type.setSelectedItem(attributeSelected.getType().toString());
        }

    }

    private void editAttribute() {
        if (attributeSelected != null) {
            this.controller.editAttribute(attributeSelected);
            cleanView();
        }
        attributeSelected = null;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - santiago storti
        panel1 = new JPanel();
        attributesText = new JLabel();
        scrollPane1 = new JScrollPane();
        attributeList = new JList();
        scrollPane3 = new JScrollPane();
        internalAttributesList = new JList();
        nameText = new JLabel();
        name = new JTextField();
        typeText = new JLabel();
        type = new JComboBox(AttributeType.attributesTypes);
        expressionText = new JLabel();
        scrollPane2 = new JScrollPane();
        expression = new JTextArea();
        addToAttibuteButton = new JButton();
        createAttributeButton = new JButton();
        editAttributeButton = new JButton();

        //======== panel1 ========
        {
            panel1.setLayout(new FormLayout(
                    "22*(default, $lcgap), 11*(default), 2*($lcgap, default), 3*(default), 3*($lcgap, default), 16*(default)",
                    "20*(default, $lgap), default"));

            //---- attributesText ----
            attributesText.setText("Attributes");
            panel1.add(attributesText, CC.xy(5, 1));

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(attributeList);
            }
            panel1.add(scrollPane1, CC.xywh(3, 3, 27, 23));

            //======== scrollPane3 ========
            {
                scrollPane3.setViewportView(internalAttributesList);
            }
            panel1.add(scrollPane3, CC.xywh(31, 3, 29, 23));

            //---- nameText ----
            nameText.setText("Name");
            panel1.add(nameText, CC.xy(66, 3));
            panel1.add(name, CC.xywh(70, 3, 5, 1));

            //---- typeText ----
            typeText.setText("Type");
            panel1.add(typeText, CC.xy(66, 5));
            panel1.add(type, CC.xy(70, 5));

            //---- expressionText ----
            expressionText.setText("Expression");
            panel1.add(expressionText, CC.xy(66, 7));

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(expression);
            }
            panel1.add(scrollPane2, CC.xywh(70, 7, 6, 6));

            //---- addToAttibuteButton ----
            addToAttibuteButton.setText("Add to selected attribute");
            panel1.add(addToAttibuteButton, CC.xy(70, 21));

            //---- createAttributeButton ----
            createAttributeButton.setText("Create Attribute");
            panel1.add(createAttributeButton, CC.xy(66, 21));

            //---- editAttributeButton ----
            editAttributeButton.setText("Edit Selected");
            panel1.add(editAttributeButton, CC.xy(72, 21));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - santiago storti
    private JPanel panel1;
    private JLabel attributesText;
    private JScrollPane scrollPane1;
    private JList attributeList;
    private JScrollPane scrollPane3;
    private JList internalAttributesList;
    private JLabel nameText;
    private JTextField name;
    private JLabel typeText;
    private JComboBox type;
    private JLabel expressionText;
    private JScrollPane scrollPane2;
    private JTextArea expression;
    private JButton addToAttibuteButton;
    private JButton createAttributeButton;
    private JButton editAttributeButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private IAttributeController controller;
    private DefaultListModel defaultListModel;
    private List<Attribute> attributeModelList;
    private Attribute attributeSelected;
}
