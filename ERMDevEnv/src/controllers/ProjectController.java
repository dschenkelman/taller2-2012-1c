package controllers;

import infrastructure.IProjectContext;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import application.IShell;

import views.IProjectView;

import controllers.factories.IDiagramControllerFactory;

public class ProjectController implements IProjectController {
	private static String DefaultDiagramName = "Principal";
	
	private IProjectContext projectContext;
	private IDiagramControllerFactory diagramControllerFactory;
	private IDiagramController diagramController;
	private TreeModel projectTree;
	private IProjectView projectView;
	private IShell shell;
	
	public ProjectController(IProjectContext projectContext, IProjectView projectView, IShell shell, IDiagramControllerFactory diagramControllerFactory) {
		this.projectContext = projectContext;
		this.diagramControllerFactory = diagramControllerFactory;
		this.shell = shell;
		this.projectView = projectView;
		this.projectView.setController(this);
	}

	public void createProject(String projectName) {
		this.projectContext.setName(projectName);
		new File(this.projectContext.getDataDirectory()).mkdirs();
		
		this.diagramController = this.diagramControllerFactory.create();
		this.diagramController.getDiagram().setName(DefaultDiagramName);
		
		this.projectTree = new DefaultTreeModel(new DefaultMutableTreeNode(this.diagramController.getDiagram()));
		this.shell.setRightContent(this.diagramController.getView());
		this.shell.activateFullSize();
	}

	public IDiagramController getCurrentDiagramController() {
		return this.diagramController;
	}

	public TreeModel getProjectTree() {
		return this.projectTree;
	}

	@Override
	public IProjectView getView() {
		return this.projectView;
	}
}
