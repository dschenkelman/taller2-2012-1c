package controllers;

import infrastructure.IProjectContext;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import controllers.factories.IDiagramControllerFactory;

public class ProjectController implements IProjectController {
	private static String DefaultDiagramName = "Principal";
	
	private IProjectContext projectContext;
	private IDiagramControllerFactory diagramControllerFactory;
	private IDiagramController diagramController;
	private TreeModel projectTree;
	
	public ProjectController(IProjectContext projectContext, IDiagramControllerFactory diagramControllerFactory) {
		this.projectContext = projectContext;
		this.diagramControllerFactory = diagramControllerFactory;
	}

	public void createProject(String projectName) {
		this.projectContext.setName(projectName);
		new File(this.projectContext.getDataDirectory()).mkdirs();
		
		this.diagramController = this.diagramControllerFactory.create();
		this.diagramController.getDiagram().setName(DefaultDiagramName);
		
		this.projectTree = new DefaultTreeModel(new DefaultMutableTreeNode(this.diagramController.getDiagram()));
	}

	public IDiagramController getCurrentDiagramController() {
		return this.diagramController;
	}

	public TreeModel getProjectTree() {
		return this.projectTree;
	}
}
