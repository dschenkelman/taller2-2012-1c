package views;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.tree.TreePath;

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
		
		this.btnCreate = new JButton("Create");
		this.add(this.btnCreate, "2, 2");
		
		this.btnOpen = new JButton("Open");
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
				String name = JOptionPane.showInputDialog(null, "Provide the project's name", "New Project", JOptionPane.QUESTION_MESSAGE);
				if (name != null){
					projectController.createProject(name);
					tree.setModel(projectController.getProjectTree());
				}
			}	
		});
		
		this.btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				String name = JOptionPane.showInputDialog(null, "Provide the project's name", "Open Project", JOptionPane.QUESTION_MESSAGE);
				if (name != null){
					try {
						projectController.openProject(name);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					tree.setModel(projectController.getProjectTree());
				}
			}	
		});
		
		this.tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				TreePath path = tree.getPathForLocation(e.getX(),e.getY());
				if (e.getClickCount() == 2) {
					projectController.changeElement(path);					
				}
			}
		});
		this.tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				TreePath path = tree.getSelectionPath();
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					projectController.deleteElement(path);
				}
			}
		});
	}
}
