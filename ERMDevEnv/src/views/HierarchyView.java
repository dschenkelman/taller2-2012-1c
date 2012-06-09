package views;

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

import controllers.IHierarchyController;

public class HierarchyView implements IHierarchyView{

	private IHierarchyController hierarchyController;
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
		this.frame1 = new JFrame();
		this.generalEntityLabel = new JLabel();
		this.specificEntitiesLabel = new JLabel();
		this.availableEntitiesLabel = new JLabel();
		this.lstAvailableEntities = new JList();
		this.lstSpecificEntities = new JList();
		this.arrAddSpecificEntities = new BasicArrowButton(BasicArrowButton.EAST);
		this.arrRemoveSpecificEntities = new BasicArrowButton(BasicArrowButton.WEST);
		this.comBoxGeneralEntity = new JComboBox();
		this.chkBoxTotal = new JCheckBox();
		this.chkBoxExclusive = new JCheckBox();
		this.createHierarchy = new JButton();
		this.cancel = new JButton();
		
		this.frame1.setTitle(HierarchyView.TITLE);
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
					// ver bien esto
					frame1.setEnabled(false);
			}
		});
		this.cancel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// mismo que arriba
				frame1.setEnabled(false);
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
		this.frame1.setVisible(true);
	}

}
