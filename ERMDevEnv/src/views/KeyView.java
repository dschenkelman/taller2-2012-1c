/*
 * Created by JFormDesigner on Wed Jun 20 16:35:42 ART 2012
 */

package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.factories.*;

import com.jgoodies.forms.layout.*;
import controllers.IKeysController;
import models.IKey;
import models.IdGroup;
import models.IdGroupCollection;

public class KeyView extends JFrame implements IKeysView {

    private IKeysController controller;
    private Iterable<IKey> possibleKeys;
    private IdGroup idGroupSelected;
    private IKey keySelectedToAdd;
    private IKey keySelectedToRemove;
    private DefaultListModel idGroupListModel;
    private DefaultListModel currentKeysListModel;
    private DefaultListModel possibleKeysListModel;

    public KeyView() {
        initComponents();
        cleanView();

        this.idGroupListModel = new DefaultListModel();
        this.idGroupList.setModel(this.idGroupListModel);
        this.idGroupList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cleanView();
                idGroupSelected = (IdGroup) idGroupList.getSelectedValue();
                refreshKeyListWithIdGroup(idGroupSelected);
            }
        });
        this.currentKeysList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                keySelectedToRemove = (IKey) currentKeysList.getSelectedValue();
            }
        });
        this.possibleKeysList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                keySelectedToAdd = (IKey) possibleKeysList.getSelectedValue();
            }
        });

        this.finsihButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        this.newIdGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idGroupListModel.addElement(new IdGroup(getNextNumber(), false));
                cleanView();
            }
        });

        this.addKeyToIdGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keySelectedToAdd != null) {
                    controller.addIdGroupToKey();
                    currentKeysListModel.addElement(keySelectedToAdd);
                    possibleKeysListModel.removeElement(keySelectedToAdd);
                    updateKeysList();
                }
            }
        });

        this.removeKeyFromIdGriupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keySelectedToRemove != null) {
                    controller.removeIdGroupFromKey();
                    possibleKeysListModel.addElement(keySelectedToRemove);
                    currentKeysListModel.removeElement(keySelectedToRemove);
                    updateKeysList();
                }
            }
        });
    }

    @Override
    public void showView() {
        this.setVisible(true);
    }

    @Override
    public void setController(IKeysController controller) {
        this.controller = controller;
    }

    @Override
    public void setPossibleKeys(Iterable<IKey> keys) {
        this.possibleKeys = keys;
        //TODO agregar los idgroup existentes
    }

    @Override
    public IdGroup getIdGroupSelected() {
        return this.idGroupSelected;
    }

    @Override
    public IKey getKeySelectedToAdd() {
        return this.keySelectedToAdd;
    }

    @Override
    public IKey getKeySelectedToRemove() {
        return this.keySelectedToRemove;
    }

    @Override
    public Iterable<IKey> getKeysOfIdGroupSelected() {
        List<IKey> iKeyList = new ArrayList<IKey>();
        for (IKey key : this.possibleKeys) {
            if (key.getIdGroup().exists(idGroupSelected.getNumber()))
                iKeyList.add(key);
        }
        return iKeyList;
    }

    private void updateKeysList() {
        keySelectedToAdd = null;
        keySelectedToRemove = null;
        possibleKeysList.updateUI();
        currentKeysList.updateUI();
    }

    private void cleanView() {
        this.currentKeysListModel = new DefaultListModel();
        this.currentKeysList.setModel(this.currentKeysListModel);
        this.possibleKeysListModel = new DefaultListModel();
        this.possibleKeysList.setModel(this.possibleKeysListModel);
        this.idGroupSelected = null;
        updateKeysList();
    }

    private void refreshKeyListWithIdGroup(IdGroup idGroupSelected) {
        Integer id = idGroupSelected.getNumber();
        for (IKey key : this.possibleKeys) {
            IdGroupCollection idGroupCollection = key.getIdGroup();
            if (idGroupCollection.exists(id)) {
                this.currentKeysListModel.addElement(key);
            } else {
                this.possibleKeysListModel.addElement(key);
            }

        }
    }

    private Integer getNextNumber() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - santiago storti
        label1 = new JLabel();
        newIdGroupButton = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        idGroupList = new JList();
        scrollPane2 = new JScrollPane();
        possibleKeysList = new JList();
        scrollPane3 = new JScrollPane();
        currentKeysList = new JList();
        addKeyToIdGroupButton = new JButton();
        removeKeyFromIdGriupButton = new JButton();
        finsihButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
                "64*(default, $lcgap), default",
                "56*(default, $lgap), default"));

        //---- label1 ----
        label1.setText("Id Groups");
        contentPane.add(label1, CC.xy(5, 5));

        //---- newIdGroupButton ----
        newIdGroupButton.setText("New id group");
        contentPane.add(newIdGroupButton, CC.xy(15, 5));

        //---- label2 ----
        label2.setText("Possible keys");
        contentPane.add(label2, CC.xy(57, 5));

        //---- label3 ----
        label3.setText("Current keys");
        contentPane.add(label3, CC.xy(111, 5));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(idGroupList);
        }
        contentPane.add(scrollPane1, CC.xywh(3, 9, 25, 102));

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(possibleKeysList);
        }
        contentPane.add(scrollPane2, CC.xywh(35, 9, 41, 102));

        //======== scrollPane3 ========
        {
            scrollPane3.setViewportView(currentKeysList);
        }
        contentPane.add(scrollPane3, CC.xywh(87, 9, 41, 100));

        //---- addKeyToIdGroupButton ----
        addKeyToIdGroupButton.setText("Add key to id group");
        contentPane.add(addKeyToIdGroupButton, CC.xy(81, 11));

        //---- removeKeyFromIdGriupButton ----
        removeKeyFromIdGriupButton.setText("Remove key from id group");
        contentPane.add(removeKeyFromIdGriupButton, CC.xy(81, 95));

        //---- finsihButton ----
        finsihButton.setText("Finish");
        contentPane.add(finsihButton, CC.xy(123, 111));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - santiago storti
    private JLabel label1;
    private JButton newIdGroupButton;
    private JLabel label2;
    private JLabel label3;
    private JScrollPane scrollPane1;
    private JList idGroupList;
    private JScrollPane scrollPane2;
    private JList possibleKeysList;
    private JScrollPane scrollPane3;
    private JList currentKeysList;
    private JButton addKeyToIdGroupButton;
    private JButton removeKeyFromIdGriupButton;
    private JButton finsihButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
