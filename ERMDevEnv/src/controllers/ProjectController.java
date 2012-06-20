package controllers;

import infrastructure.IProjectContext;
import infrastructure.visual.DiagramTreeNode;

import java.io.File;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import models.Diagram;
import models.Entity;
import models.Relationship;

import application.IShell;

import views.IProjectView;

import controllers.factories.IDiagramControllerFactory;
import controllers.listeners.IDiagramEventListener;

public class ProjectController implements IProjectController, IDiagramEventListener {
	private static String DefaultDiagramName = "Principal";
	
	private IProjectContext projectContext;
	private IDiagramControllerFactory diagramControllerFactory;
	private IDiagramController diagramController;
	private DefaultTreeModel projectTree;
	private IProjectView projectView;
	private IShell shell;
	private DiagramTreeNode currentDiagramNode;
	
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
		this.diagramController.addListener(this);
		
		this.currentDiagramNode = new DiagramTreeNode(this.diagramController.getDiagram());
		this.projectTree = new DefaultTreeModel(this.currentDiagramNode);
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

	@Override
	public void handleEntityAdded(Diagram diagram, Entity entity) {
		this.currentDiagramNode.addEntity(entity, this.projectTree);
	}

	@Override
	public void handleRelationshipAdded(Diagram diagram, Relationship relationship) {
		this.currentDiagramNode.addRelationship(relationship, this.projectTree);
	}

	@Override
	public void handleSubDiagramCreated(Diagram diagram, String diagramName) {
		IDiagramController childDiagramController = this.diagramControllerFactory.create();
		childDiagramController.getDiagram().setName(diagramName);
		DiagramTreeNode childDiagramNode = this.currentDiagramNode.addSubdiagram(childDiagramController.getDiagram());
		
		this.shell.setRightContent(childDiagramController.getView());
		this.diagramController = childDiagramController;
		this.diagramController.addListener(this);
		this.currentDiagramNode = childDiagramNode;
	}

	@Override
	public void changeElement(TreePath treePath) {
		this.projectContext.clear();
		for (Object o : treePath.getPath()) {
			if (o instanceof DiagramTreeNode) {
				DiagramTreeNode node = (DiagramTreeNode) o;
				Diagram diagram = (Diagram) node.getUserObject();
				this.projectContext.addContextDiagram(diagram);
			}
		}
	}
}
