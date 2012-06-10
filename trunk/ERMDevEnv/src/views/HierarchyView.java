package views;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
	private JCheckBox chkBoxTotal;
	private JCheckBox chkBoxExclusive;
	private JButton createHierarchy;
	private JButton cancel;
	
	public HierarchyView()
	{
		//this.availableEntities = (List<Entity>) this.hierarchyController.getAvailableEntities();
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
		DefaultListModel listModel = new DefaultListModel();
		if (this.availableEntities != null)
			for (Entity entity : this.availableEntities)
			{
				this.comBoxGeneralEntity.addItem(entity);
				listModel.addElement(entity);
			}
		this.specificEntitiesLabel = new JLabel("Entidades Específicas");
		this.availableEntitiesLabel = new JLabel("Entidades Disponibles");
		this.lstAvailableEntities = new JList(listModel);
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
		
		// checkBoxs
		this.chkBoxTotal = new JCheckBox("Total");
		container.add(this.chkBoxTotal, CC.xywh(88, 8, 30, 10));
		
		this.chkBoxExclusive = new JCheckBox("Exclusiva");
		container.add(this.chkBoxExclusive, CC.xywh(88, 18, 30, 10));
		
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
					listModelAvailableEntities.removeElement(entity);
					listModelSpecificEntities.addElement(entity);
					try {
						hierarchyController.addSpecificEntity(entity);
					} catch (Exception e1) {
					}
				}
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
					hierarchyController.removeSpecificEntity(entity);
				}
			}
		});
		
		// check box
		this.chkBoxTotal.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
					hierarchyController.setTotal(false);
				else
					hierarchyController.setTotal(true);
			}
		});
		this.chkBoxExclusive.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
					hierarchyController.setExclusive(false);
				else
					hierarchyController.setExclusive(true);
			}
		});
		
		// general entity
		this.comBoxGeneralEntity.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comBox = (JComboBox) e.getSource();
				if (comBox.getSelectedIndex() != 0)
				{
					Entity entity = (Entity) comBox.getSelectedItem();
					hierarchyController.setGeneralEntity(entity);
					System.out.print(entity.toString());
				}else{
					hierarchyController.setGeneralEntity(null);
					System.out.print("null");
				}
			}
		});
	}

	@Override
	public void showView() {
		this.frame1.pack();
		this.frame1.setVisible(true);
	}

}
