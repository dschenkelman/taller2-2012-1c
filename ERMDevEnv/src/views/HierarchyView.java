package views;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicArrowButton;

import models.EntityCollection;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

import controllers.IHierarchyController;

public class HierarchyView implements IHierarchyView{

	private IHierarchyController hierarchyController;
	private EntityCollection availableEntities;
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
		//this.availableEntities = this.hierarchyController.getAvailableEntities();
		// frame
		this.frame1 = new JFrame(HierarchyView.TITLE);
		this.frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Container container = frame1.getContentPane();
		container.setLayout(new FormLayout(
                "100*(default, $lcgap), default",
                "100*(default, $lgap), default"));
		
		// general entity
		// le falta agregar la lista de entidades disponibles
		this.comBoxGeneralEntity = new JComboBox();
		this.generalEntityLabel = new JLabel("Entidad General");
		this.generalEntityLabel.setLabelFor(this.comBoxGeneralEntity);
		container.add(this.generalEntityLabel, CC.xywh(2, 4, 40, 10));
		container.add(this.comBoxGeneralEntity, CC.xywh(2, 14, 40, 10));
		
		// specific entities
		this.specificEntitiesLabel = new JLabel("Entidades Específicas");
		this.availableEntitiesLabel = new JLabel("Entidades Disponibles");
		this.lstAvailableEntities = new JList();
		this.lstSpecificEntities = new JList();
		
		// arrows
		this.arrAddSpecificEntities = new BasicArrowButton(BasicArrowButton.EAST);
		container.add(this.arrAddSpecificEntities, CC.xywh(60, 55, 8, 15));
		
		this.arrRemoveSpecificEntities = new BasicArrowButton(BasicArrowButton.WEST);
		container.add(this.arrRemoveSpecificEntities, CC.xywh(60, 75, 8, 15));
		
		// checkBoxs
		this.chkBoxTotal = new JCheckBox("Total");
		this.chkBoxExclusive = new JCheckBox("Exclusiva");
		
		// accept and cancel
		this.createHierarchy = new JButton("Aceptar");
		container.add(this.createHierarchy, CC.xywh(88, 120, 30, 10));
		
		this.cancel = new JButton("Cancelar");
		container.add(this.cancel, CC.xywh(120, 120, 30, 10));
	}
	
	@Override
	public boolean isVisible() {
		return this.frame1.isVisible();
	}

	@Override
	public void setController(IHierarchyController controller) {
		this.hierarchyController = controller;
		
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
		this.arrAddSpecificEntities.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			
			}
		});
		this.arrRemoveSpecificEntities.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void showView() {
		this.frame1.pack();
		this.frame1.setVisible(true);
	}

}
