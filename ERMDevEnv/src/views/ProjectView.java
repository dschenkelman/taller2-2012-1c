package views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import controllers.IProjectController;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class ProjectView extends JPanel implements IProjectView {

	private JTree tree;
	private JButton btnOpen;
	private JButton btnCreate;
	private IProjectController projectController;

	/**
	 * Create the panel.
	 */
	public ProjectView() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		this.btnCreate = new JButton("Crear");
		this.add(this.btnCreate, "2, 2");
		
		this.btnOpen = new JButton("Abrir");
		this.add(this.btnOpen, "6, 2");
		
		this.tree = new JTree();
		tree.setModel(null);
		this.add(this.tree, "2, 4, 7, 1, fill, fill");
	}

	@Override
	public void setController(IProjectController controller) {
		this.projectController = controller;
		
		this.btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				String name = JOptionPane.showInputDialog(null, "Ingrese el nombre del proyecto", "Nuevo Proyecto", JOptionPane.QUESTION_MESSAGE);
				if (name != null){
					projectController.createProject(name);
					tree.setModel(projectController.getProjectTree());
				}
			}	
		});
	}

}
