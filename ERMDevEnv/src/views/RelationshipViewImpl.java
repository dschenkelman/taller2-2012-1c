package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controllers.IAttributeController;
import controllers.IRelationshipController;
import controllers.IRelationshipEntityController;
import controllers.tests.mocks.MockProjectContext;
import controllers.tests.mocks.MockRelationshipController;
import controllers.tests.mocks.MockRelationshipEntityController;

public class RelationshipViewImpl extends RelationshipViewAbstract implements IRelationshipView{

	IRelationshipController relController;
	IAttributeController attController;
	IRelationshipEntityController relEntController;
	RelationshipEntityViewImpl relEntView;
	IAttributeView attView;
	
	
	RelationshipViewImpl (RelationshipEntityViewImpl relEntView, AttributeView attView) {
		super ( relEntView,  attView);
	}
	
	
	public static void main(String args[]) {
		
		AttributeView attView = null;
		RelationshipEntityViewImpl relEntView = null;
		
		RelationshipViewImpl vent = null;//new RelationshipViewImpl();
		vent.setSize(800, 500);
		
		MockRelationshipController mockRelController = new MockRelationshipController();
		
		vent.setController(mockRelController);
				
		
		MockProjectContext pContext = new MockProjectContext () ;
		
		MockRelationshipEntityController controller = new MockRelationshipEntityController(pContext);
		
		controller.createTestingList();
		
		
		vent.setVisible(true);
		vent.setDefaultCloseOperation(vent.EXIT_ON_CLOSE);
	}
	
	@Override
	protected void setUpCompontenents (RelationshipEntityViewImpl relEntView, AttributeView attView) {
		//add listeners
		checkBox1.addItemListener(new CheckListener());
		this.textFieldName.addActionListener(new MyLabelNameListener());
		this.button1.addActionListener(new ActionAdd ());
		//set the panels
		this.panelAttributes = relEntView;
		this.panelAttributes = attView.getFrame();
		
		
	}
	
	@Override
	public IRelationshipController getController() {
		return relController;
	}

	@Override
	public void setController(IRelationshipController relationshipController) {
		relController = relationshipController;		
	}

	@Override
	public void addRelationshipEntityView (RelationshipEntityViewImpl relEntView) {
		this.relEntView = relEntView;
		this.panelEntities = relEntView;
	}
	
	@Override
	public void addAttributeView (IAttributeView attview) {
		this.attView = attview;
		this.panelAttributes = attview.getFrame();
	}
	
	private void showErrorDialog (String msg) {
		JFrame frame = new JFrame ();
		frame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(frame, msg,"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	private class CheckListener implements ItemListener {
		/**Pregunta al controlador si debo poder seleccionar el item*/
		@Override
		public void itemStateChanged(ItemEvent e) {
			try {
				relController.isComposition(checkBox1.isSelected());
			}catch (Exception exception) {
				showErrorDialog(exception.getMessage());
				checkBox1.setSelected(false);
			}
		}
	}
	
	private class ActionAdd implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				relController.add();
			} catch (Exception e1) {
				showErrorDialog(e1.getMessage());
			}
		}
	}

	private class MyLabelNameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			relController.setName(textFieldName.getText());
		}
	}
		
}
