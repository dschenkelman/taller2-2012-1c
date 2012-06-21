package controllers;

import infrastructure.IProjectContext;
import infrastructure.visual.DiagramTreeNode;

import java.io.File;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlFileManager;
import persistence.IXmlManager;

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

	private IXmlFileManager xmlFileManager;

	private IXmlManager<Diagram> diagramXmlManager;
	
	public ProjectController(IProjectContext projectContext, IProjectView projectView, 
			IShell shell, IDiagramControllerFactory diagramControllerFactory,
			IXmlFileManager xmlFileManager, IXmlManager<Diagram> diagramXmlManager) {
		this.projectContext = projectContext;
		this.diagramControllerFactory = diagramControllerFactory;
		this.shell = shell;
		this.projectView = projectView;
		this.projectView.setController(this);
		this.xmlFileManager = xmlFileManager;
		this.diagramXmlManager = diagramXmlManager;
	}

	public void createProject(String projectName) {
		this.projectContext.setName(projectName);
		new File(this.projectContext.getDataDirectory()).mkdirs();
		
		this.diagramController = this.diagramControllerFactory.create();
		this.diagramController.getDiagram().setName(DefaultDiagramName);
		this.diagramController.addListener(this);
		
		Diagram mainDiagram = this.diagramController.getDiagram();
		
		this.currentDiagramNode = new DiagramTreeNode(this.diagramController.getDiagram());
		this.projectTree = new DefaultTreeModel(this.currentDiagramNode);
		
		this.projectContext.addContextDiagram(mainDiagram);
		this.projectContext.addProjectDiagram(mainDiagram);
		
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
		Diagram parentDiagram = this.diagramController.getDiagram();
		
		IDiagramController childDiagramController = this.diagramControllerFactory.create();
		Diagram childDiagram = childDiagramController.getDiagram();
		
		childDiagram.setName(diagramName);
		
		parentDiagram.addSubDiagram(childDiagram);
		DiagramTreeNode childDiagramNode = this.currentDiagramNode.addSubdiagram(childDiagram);
		
		this.projectContext.addContextDiagram(childDiagram);
		this.projectContext.addProjectDiagram(childDiagram);
		
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

	@Override
	public void openProject(String projectName) throws Exception {
		this.projectContext.setName(projectName);
		this.loadDiagram(DefaultDiagramName, null);
	}
	
	private void loadDiagram(String diagramName, DiagramTreeNode parentTreeNode) throws Exception{
		Document document = this.xmlFileManager.read(this.projectContext.getDataDirectory() + "/" + diagramName + "-comp");
		Element documentElement = document.getDocumentElement();
		Diagram diagram = this.diagramXmlManager.getItemFromXmlElement(documentElement);
		diagram.setName(diagramName);
		
		DiagramTreeNode currentTreeNode;
		
		if (diagramName.equalsIgnoreCase(DefaultDiagramName)){
			this.projectContext.addContextDiagram(diagram);
			currentTreeNode = new DiagramTreeNode(diagram);
			this.currentDiagramNode = currentTreeNode;
			this.projectTree = new DefaultTreeModel(this.currentDiagramNode);
		}
		else
		{
			currentTreeNode = parentTreeNode.addSubdiagram(diagram);
		}
		
		this.projectContext.addProjectDiagram(diagram);
		
		for (String childDiagramName : diagram.getSubDiagramNames()) {
			this.loadDiagram(childDiagramName, currentTreeNode);
		}
	}
}
