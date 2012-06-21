package views;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.plaf.basic.BasicArrowButton;

import models.Entity;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

import controllers.IHierarchyController;

public class HierarchyView implements IHierarchyView{

	private IHierarchyController hierarchyController;
	private List<Entity> availableEntities;
	private static String TITLE = "Hierarchy Creation";
	private JFrame frame1; 
	private JLabel generalEntityLabel;
	private JLabel specificEntitiesLabel;
	private JLabel availableEntitiesLabel;
	private JList lstAvailableEntities;
	private JList lstSpecificEntities;
	private JButton arrAddSpecificEntities;
	private JButton arrRemoveSpecificEntities;
	private JComboBox comBoxGeneralEntity;
	private JRadioButton btnTotal;
	private JRadioButton btnParcial;
	private JRadioButton btnExclusive;
	private JRadioButton btnInclusive;
	private JButton createHierarchy;
	private JButton cancel;
	
	public HierarchyView()
	{
		// frame
		this.frame1 = new JFrame(HierarchyView.TITLE);
		this.frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Container container = frame1.getContentPane();
		container.setLayout(new FormLayout(
                "100*(default, $lcgap), default",
                "100*(default, $lgap), default"));
		
		// general entity
		this.comBoxGeneralEntity = new JComboBox();
		this.comBoxGeneralEntity.addItem("None");
		this.generalEntityLabel = new JLabel("Entidad General");
		this.generalEntityLabel.setLabelFor(this.comBoxGeneralEntity);
		container.add(this.generalEntityLabel, CC.xywh(2, 4, 40, 10));
		container.add(this.comBoxGeneralEntity, CC.xywh(2, 14, 40, 10));
		
		// specific entities
		this.specificEntitiesLabel = new JLabel("Entidades Específicas");
		this.availableEntitiesLabel = new JLabel("Entidades Disponibles");
		this.lstAvailableEntities = new JList(new DefaultListModel());
		this.lstSpecificEntities = new JList(new DefaultListModel());
		this.availableEntitiesLabel.setLabelFor(this.lstAvailableEntities);
		this.specificEntitiesLabel.setLabelFor(this.lstSpecificEntities);
		container.add(this.availableEntitiesLabel, CC.xywh(2, 30, 50, 10));
		container.add(this.lstAvailableEntities, CC.xywh(2, 40, 50, 70));
		container.add(this.specificEntitiesLabel, CC.xywh(80, 30, 50, 10));
		container.add(this.lstSpecificEntities, CC.xywh(80, 40, 50, 70));
		
		// arrows
		this.arrAddSpecificEntities = new BasicArrowButton(BasicArrowButton.EAST);
		container.add(this.arrAddSpecificEntities, CC.xywh(62, 55, 8, 15));
		
		this.arrRemoveSpecificEntities = new BasicArrowButton(BasicArrowButton.WEST);
		container.add(this.arrRemoveSpecificEntities, CC.xywh(62, 75, 8, 15));
		
		// Button group
		this.btnTotal = new JRadioButton("Total");
		container.add(this.btnTotal, CC.xywh(88, 8, 30, 10));
		
		this.btnParcial = new JRadioButton("Parcial");
		container.add(this.btnParcial, CC.xywh(88, 18, 30, 10));
		
		this.btnExclusive = new JRadioButton("Exclusive");
		container.add(this.btnExclusive, CC.xywh(58, 8, 30, 10));
		
		this.btnInclusive = new JRadioButton("Inclusive");
		container.add(this.btnInclusive, CC.xywh(58, 18, 30, 10));
		
		ButtonGroup group1 = new ButtonGroup();
		ButtonGroup group2 = new ButtonGroup();
		
		group1.add(this.btnTotal);
		group1.add(this.btnParcial);
		group2.add(this.btnExclusive);
		group2.add(this.btnInclusive);
		
		// accept and cancel
		this.createHierarchy = new JButton("Aceptar");
		container.add(this.createHierarchy, CC.xywh(68, 120, 30, 10));
		
		this.cancel = new JButton("Cancelar");
		container.add(this.cancel, CC.xywh(100, 120, 30, 10));
	}
	
	@Override
	public boolean isVisible() {
		return this.frame1.isVisible();
	}

	@Override
	public void setController(IHierarchyController controller) {
		this.hierarchyController = controller;
		
		// accept and cancel
		this.createHierarchy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (hierarchyController.addHierarchy())
					frame1.setVisible(false);
			}
		});
		this.cancel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame1.dispose();
			}
		});
		
		// arrows
		this.arrAddSpecificEntities.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel listModelSpecificEntities = (DefaultListModel) lstSpecificEntities.getModel();
				DefaultListModel listModelAvailableEntities = (DefaultListModel) lstAvailableEntities.getModel();
		
				for (int i = 0; i < lstAvailableEntities.getSelectedValues().length; i++)
				{
					Entity entity = (Entity) lstAvailableEntities.getSelectedValues()[i];
					try {
						if (hierarchyController.addSpecificEntity(entity)) {
							comBoxGeneralEntity.removeItem(entity);
							listModelAvailableEntities.removeElement(entity);
							listModelSpecificEntities.addElement(entity);
						}
					} catch (Exception e1) {
					}
				}
				checkCountSpecificEntities(listModelSpecificEntities.size());
			}
		});
		this.arrRemoveSpecificEntities.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel listModelSpecificEntities = (DefaultListModel) lstSpecificEntities.getModel();
				DefaultListModel listModelAvailableEntities = (DefaultListModel) lstAvailableEntities.getModel();
				for (int i = 0; i < lstSpecificEntities.getSelectedValues().length; i++)
				{
					Entity entity = (Entity) lstSpecificEntities.getSelectedValues()[i];
					listModelSpecificEntities.removeElement(entity);
					listModelAvailableEntities.addElement(entity);
					comBoxGeneralEntity.addItem(entity);
					hierarchyController.removeSpecificEntity(entity);
				}
				checkCountSpecificEntities(listModelSpecificEntities.getSize());
			}
		});
		
		// button group
		this.btnTotal.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					hierarchyController.setTotal(true);
				else
					hierarchyController.setTotal(false);
			}
		});
		this.btnExclusive.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					hierarchyController.setExclusive(true);
				else
					hierarchyController.setExclusive(false);
			}
		});
		
		// general entity
		this.comBoxGeneralEntity.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comBox = (JComboBox) e.getSource();
				
				DefaultComboBoxModel combGeneralMdl = (DefaultComboBoxModel) comBoxGeneralEntity.getModel();
				DefaultListModel lstAvailableMdl = (DefaultListModel) lstAvailableEntities.getModel();
				
				for (Entity entity : availableEntities)
					if (combGeneralMdl.getIndexOf(entity) != -1 && lstAvailableMdl.indexOf(entity) == -1)
						lstAvailableMdl.addElement(entity);
				
				if (comBox.getSelectedIndex() != 0)
				{
					Entity entity = (Entity) comBox.getSelectedItem();
					hierarchyController.setGeneralEntity(entity);
					lstAvailableMdl.removeElement(entity);
				}else{
					hierarchyController.setGeneralEntity(null);
				}
			}
		});
	}

	private void checkCountSpecificEntities(int size) {
		if (size == 1) {
			btnParcial.doClick();
			btnExclusive.doClick();
			btnParcial.setEnabled(false);
			btnTotal.setEnabled(false);
			btnExclusive.setEnabled(false);
			btnInclusive.setEnabled(false);
		}else {
			btnParcial.setEnabled(true);
			btnTotal.setEnabled(true);
			btnExclusive.setEnabled(true);
			btnInclusive.setEnabled(true);
		}
	}
	
	@Override
	public void showView() {
		this.frame1.pack();
		this.frame1.setVisible(true);
	}

	@Override
	public void update() {
		this.availableEntities = (List<Entity>) this.hierarchyController.getAvailableEntities();
		
		//specific entities
		DefaultComboBoxModel combGeneralMdl = (DefaultComboBoxModel) this.comBoxGeneralEntity.getModel();
		DefaultListModel lstAvailableMdl = (DefaultListModel) this.lstAvailableEntities.getModel();
		DefaultListModel lstSpecificMdl = (DefaultListModel) this.lstSpecificEntities.getModel();

		for (Entity entity : this.availableEntities) {
			if (this.hierarchyController.hasSpecificEntity(entity)) {
				lstSpecificMdl.addElement(entity);
				continue;
			}
			if (this.hierarchyController.isGeneralEntity(entity)) {
				combGeneralMdl.addElement(entity);
				continue;
			}
			combGeneralMdl.addElement(entity);
			lstAvailableMdl.addElement(entity);
		}
	}

	@Override
	public void create() {
		this.availableEntities = (List<Entity>) this.hierarchyController.getAvailableEntities();
		//general entity and available entities
		DefaultListModel listModel = (DefaultListModel) this.lstAvailableEntities.getModel();
		for (Entity entity : this.availableEntities)
		{
			this.comBoxGeneralEntity.addItem(entity);
			listModel.addElement(entity);
		}
	}

}