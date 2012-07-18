package controllers;

import infrastructure.IFileSystemService;
import infrastructure.IProjectContext;
import infrastructure.visual.DiagramTreeNode;

import java.io.File;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;

import models.Diagram;
import models.Entity;
import models.Hierarchy;
import models.Relationship;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlFileManager;
import persistence.IXmlManager;
import views.IProjectView;
import application.IShell;
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

	private IFileSystemService fileSystemService;
	
	public ProjectController(IProjectContext projectContext, IProjectView projectView, 
			IShell shell, IDiagramControllerFactory diagramControllerFactory,
			IXmlFileManager xmlFileManager, IXmlManager<Diagram> diagramXmlManager, 
			IFileSystemService fileSystemService) {
		this.projectContext = projectContext;
		this.diagramControllerFactory = diagramControllerFactory;
		this.shell = shell;
		this.projectView = projectView;
		this.projectView.setController(this);
		this.xmlFileManager = xmlFileManager;
		this.diagramXmlManager = diagramXmlManager;
		this.fileSystemService = fileSystemService;
	}

	public void createProject(String projectName) {
		this.projectContext.setName(projectName);
		new File(this.projectContext.getDataDirectory()).mkdirs();
		
		this.diagramController = this.diagramControllerFactory.create();
		this.diagramController.getDiagram().setName(DefaultDiagramName);
		this.diagramController.addListener(this);
		
		Diagram mainDiagram = this.diagramController.getDiagram();
		
		this.currentDiagramNode = new DiagramTreeNode(this.diagramController.getDiagram(), this.projectContext);
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
		try {
			this.diagramController.save();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		DiagramTreeNode childDiagramNode = this.currentDiagramNode.addSubdiagram(childDiagram);
		
		this.projectContext.addContextDiagram(childDiagram);
		this.projectContext.addProjectDiagram(childDiagram);
		
		this.shell.setRightContent(childDiagramController.getView());
		this.diagramController = childDiagramController;
		
		try {
			this.diagramController.save();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		this.diagramController.addListener(this);
		this.currentDiagramNode = childDiagramNode;
	}

	@Override
	public void changeElement(TreePath treePath) {
		if (treePath == null)
			return;
		this.projectContext.clearContextDiagrams();
		Diagram diagramToLoad = null;
		for (Object o : treePath.getPath()) {
			if (o instanceof DiagramTreeNode) {
				DiagramTreeNode node = (DiagramTreeNode) o;
				Diagram diagram = (Diagram) node.getUserObject();
				diagramToLoad = diagram;
				this.projectContext.addContextDiagram(diagram);
			}
		}
		
		IDiagramController newController = this.diagramControllerFactory.create();
		
		try {
			this.diagramController.save();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		this.diagramController = newController;
		this.diagramController.addListener(this);
		this.diagramController.load(diagramToLoad);
		this.shell.setRightContent(this.diagramController.getView());
		
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
		Object o = node.getUserObject();
		
		if (o instanceof Entity) {
			this.diagramController.updateEntity((Entity) o);
			return;	
		}
		if (o instanceof Relationship) {
			this.diagramController.updateRelationship((Relationship) o);
			return;
		}
		if (o instanceof Hierarchy) {
			this.diagramController.updateHierarchy((Hierarchy) o);
			return;
		}
	}

	@Override
	public boolean openProject(String projectName) throws Exception {
		this.projectContext.setName(projectName);
		if (!this.fileSystemService.exists(this.projectContext.getDataDirectory(), DefaultDiagramName)) {
			return false;
		}
		this.loadDiagram(DefaultDiagramName, null);
		this.diagramController = this.diagramControllerFactory.create();
		this.diagramController.addListener(this);
		
		this.diagramController.load(this.projectContext.getContextDiagram(DefaultDiagramName));
		
		this.shell.setRightContent(this.diagramController.getView());
		this.shell.activateFullSize();
		return true;
	}
	
	private void loadDiagram(String diagramName, DiagramTreeNode parentTreeNode) throws Exception{
		Document document = this.xmlFileManager.read(this.projectContext.getDataDirectory() + "/" + diagramName + "-comp");
		Element documentElement = document.getDocumentElement();
		Diagram diagram = this.diagramXmlManager.getItemFromXmlElement(documentElement);
		diagram.setName(diagramName);
		
		DiagramTreeNode currentTreeNode;
		
		if (diagramName.equalsIgnoreCase(DefaultDiagramName)){
			this.projectContext.addContextDiagram(diagram);
			currentTreeNode = new DiagramTreeNode(diagram, this.projectContext);
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

	@Override
	public void handleHierarchyAdded(Diagram diagram, Hierarchy hierarchy) {
		this.currentDiagramNode.addHierarchy(hierarchy, this.projectTree);
	}
}
