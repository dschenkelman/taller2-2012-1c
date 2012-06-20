package controllers.tests;


import infrastructure.visual.DiagramTreeNode;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import models.Diagram;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.IProjectController;
import controllers.ProjectController;
import controllers.factories.tests.mocks.MockDiagramControllerFactory;
import controllers.tests.mocks.MockDiagramController;
import controllers.tests.mocks.MockDiagramXmlManager;
import controllers.tests.mocks.MockProjectContext;
import controllers.tests.mocks.MockProjectView;
import controllers.tests.mocks.MockShell;

public class ProjectControllerTestCase {

	private MockProjectContext projectContext;
	
	private MockDiagramControllerFactory diagramControllerFactory;

	private MockDiagramController diagramController;
	
	private MockProjectView projectView;
	
	private MockShell shell;
	
	private MockDiagramXmlManager diagramXmlManager;
	
	@Before
	public void setUp() throws Exception {
		this.projectContext = new MockProjectContext();
		this.diagramControllerFactory = new MockDiagramControllerFactory();
		this.diagramController = new MockDiagramController();
		this.diagramControllerFactory.setController(this.diagramController);
		this.projectView = new MockProjectView();
		this.shell = new MockShell();
		this.diagramXmlManager = new MockDiagramXmlManager();
	}
	
	@Test
	public void testShouldSetControllerToViewWhenConstructing(){
		Assert.assertNull(this.projectView.getController());
		IProjectController controller = this.createController(); 
		Assert.assertSame(controller, this.projectView.getController());
	}
	
	@Test
	public void testShouldReturnView(){
		IProjectController controller = this.createController(); 
		Assert.assertSame(this.projectView, controller.getView());
	}
	
	@Test
	public void testShouldCreateDirectoryForProject(){
		String projectName = UUID.randomUUID().toString();
		ProjectController controller = this.createController();
		Assert.assertFalse(fileExists(projectName));
		controller.createProject(projectName);
		Assert.assertTrue(fileExists(projectName));
		Assert.assertTrue(fileExists(projectName + "/Datos"));
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldSetNameToProjectContext(){
		String projectName = UUID.randomUUID().toString();
		Assert.assertNull(this.projectContext.getName());
		Assert.assertNull(this.projectContext.getDataDirectory());
		
		ProjectController controller = this.createController();
		controller.createProject(projectName);
		
		Assert.assertEquals(projectName, this.projectContext.getName());
		Assert.assertEquals(projectName + "/Datos", this.projectContext.getDataDirectory());
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldSetCurrentDiagramNameToPrincipal(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		
		Assert.assertNull(controller.getCurrentDiagramController());
		controller.createProject(projectName);
		
		Assert.assertSame(diagramController, controller.getCurrentDiagramController());
		Assert.assertEquals("Principal", diagramController.getDiagram().getName());
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddDiagramAsTreeModelRoot(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		
		Assert.assertNull(controller.getProjectTree());
		
		controller.createProject(projectName);
		
		Assert.assertSame(this.diagramController.getDiagram(), ((DefaultMutableTreeNode)controller.getProjectTree().getRoot()).getUserObject());
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddSelfAsListenerToDiagram(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		
		Assert.assertEquals(0, this.diagramController.getListeners().size());
		
		controller.createProject(projectName);
		
		Assert.assertEquals(1, this.diagramController.getListeners().size());
		Assert.assertSame(controller, this.diagramController.getListeners().get(0));
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldSetControllerViewAsShellRightContent(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		
		Assert.assertNull(this.shell.getRightContent());
		
		controller.createProject(projectName);
		
		Assert.assertNotNull(this.shell.getRightContent());
		Assert.assertSame(this.diagramController.getView(), this.shell.getRightContent());
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldExpandShellToFullSize(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		
		Assert.assertEquals(0, this.shell.getFullSizeCalls());
		
		controller.createProject(projectName);
		
		Assert.assertEquals(1, this.shell.getFullSizeCalls());
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddEntityToDiagramTreeNodeWhenEntityIsAddedToDiagram(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		controller.createProject(projectName);
		
		Entity entity = new Entity("Product");
		controller.handleEntityAdded(this.diagramController.getDiagram(), entity);

		DefaultMutableTreeNode root = (DefaultMutableTreeNode)controller.getProjectTree().getRoot();
		
		Assert.assertNotNull(this.getNodeChildWithObject(root, "Entidades", entity));
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddRelationshipToDiagramTreeNodeWhenRelationshipIsAddedToDiagram() throws Exception{
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		controller.createProject(projectName);
		
		Relationship relationship = new Relationship(new RelationshipEntity(new Entity("E1")), new RelationshipEntity(new Entity("E2")));
		controller.handleRelationshipAdded(this.diagramController.getDiagram(), relationship);

		DefaultMutableTreeNode root = (DefaultMutableTreeNode)controller.getProjectTree().getRoot();
		
		Assert.assertNotNull(this.getNodeChildWithObject(root, "Relaciones", relationship));
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddDiagramToParentDiagramInTree(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		controller.createProject(projectName);
		
		Diagram subDiagram = new Diagram();
		MockDiagramController childController = new MockDiagramController();
		childController.setDiagram(subDiagram);
		this.diagramControllerFactory.setController(childController);
		
		controller.handleSubDiagramCreated(controller.getCurrentDiagramController().getDiagram(), "ChildDiagram");
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)controller.getProjectTree().getRoot();
		
		Assert.assertNotNull(this.getNodeChildWithObject(root, "Sub-Diagramas", subDiagram));
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldSetControllerViewAsShellRightContentWhenCreatingSubDiagram(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
				
		controller.createProject(projectName);
		
		Diagram subDiagram = new Diagram();
		MockDiagramController childController = new MockDiagramController();
		childController.setDiagram(subDiagram);
		this.diagramControllerFactory.setController(childController);
		
		Assert.assertNotNull(this.shell.getRightContent());
		Assert.assertSame(this.diagramController.getView(), this.shell.getRightContent());
		
		controller.handleSubDiagramCreated(controller.getCurrentDiagramController().getDiagram(), "ChildDiagram");
		
		Assert.assertEquals(subDiagram.getName(), "ChildDiagram");
		Assert.assertNotNull(this.shell.getRightContent());
		Assert.assertSame(childController.getView(), this.shell.getRightContent());
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldChangeCurrentControllerToChildController(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
				
		controller.createProject(projectName);
		
		Diagram subDiagram = new Diagram();
		MockDiagramController childController = new MockDiagramController();
		childController.setDiagram(subDiagram);
		this.diagramControllerFactory.setController(childController);
		
		Assert.assertNotSame(childController, controller.getCurrentDiagramController());
		
		controller.handleSubDiagramCreated(controller.getCurrentDiagramController().getDiagram(), "ChildDiagram");
		
		Assert.assertSame(childController, controller.getCurrentDiagramController());
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldChangeCurrentDiagramNodeWhenCreatingSubDiagram(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
				
		controller.createProject(projectName);
		
		Diagram subDiagram = new Diagram();
		MockDiagramController childController = new MockDiagramController();
		childController.setDiagram(subDiagram);
		this.diagramControllerFactory.setController(childController);
		
		controller.handleSubDiagramCreated(controller.getCurrentDiagramController().getDiagram(), "ChildDiagram");
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)controller.getProjectTree().getRoot();
		
		DefaultMutableTreeNode subDiagramNode = this.getNodeChildWithObject(root, "Sub-Diagramas", subDiagram);
		Assert.assertNotNull(subDiagramNode);
		
		Entity entity = new Entity("Product");
		controller.handleEntityAdded(this.diagramController.getDiagram(), entity);

		Assert.assertNotNull(this.getNodeChildWithObject(subDiagramNode, "Entidades", entity));
		
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddListenerToChildController(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		controller.createProject(projectName);
		
		Diagram subDiagram = new Diagram();
		MockDiagramController childController = new MockDiagramController();
		childController.setDiagram(subDiagram);
		this.diagramControllerFactory.setController(childController);
		
		controller.handleSubDiagramCreated(controller.getCurrentDiagramController().getDiagram(), "ChildDiagram");
		
		Assert.assertEquals(1, childController.getListeners().size());
		Assert.assertSame(controller, childController.getListeners().get(0));
			
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddDiagramToGlobalAndContextDiagramCollectionsWhenChildDiagramIsCreated(){
		String projectName = UUID.randomUUID().toString();
		
		ProjectController controller = this.createController();
		controller.createProject(projectName);
		
		Diagram subDiagram = new Diagram();
		MockDiagramController childController = new MockDiagramController();
		childController.setDiagram(subDiagram);
		this.diagramControllerFactory.setController(childController);
		
		Assert.assertEquals(1, this.projectContext.getGlobalDiagrams().size());
		Assert.assertEquals(1, this.projectContext.getContextDiagrams().size());
		
		controller.handleSubDiagramCreated(controller.getCurrentDiagramController().getDiagram(), "ChildDiagram");
		
		Assert.assertEquals(2, this.projectContext.getGlobalDiagrams().size());
		Assert.assertEquals(2, this.projectContext.getContextDiagrams().size());
			
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddDiagramToGlobalAndContextDiagramCollectionsWhenCreating(){
		String projectName = UUID.randomUUID().toString();
		
		Assert.assertEquals(0, this.projectContext.getGlobalDiagrams().size());
		Assert.assertEquals(0, this.projectContext.getContextDiagrams().size());
		
		ProjectController controller = this.createController();
		controller.createProject(projectName);
		
		Assert.assertEquals(1, this.projectContext.getGlobalDiagrams().size());
		Assert.assertEquals(1, this.projectContext.getContextDiagrams().size());
			
		deleteFile(projectName + "/Datos");
		deleteFile(projectName);
	}
	
	@Test
	public void testShouldAddDiagramsInTreeNodeToProjectContext() {
		Diagram diagram1 = new Diagram();
		Diagram diagram2 = new Diagram();
		Diagram diagram3 = new Diagram();
		diagram1.setName("1");
		diagram2.setName("2");
		diagram3.setName("3");
		DiagramTreeNode tree1 = new DiagramTreeNode(diagram1);
		DiagramTreeNode tree2 = new DiagramTreeNode(diagram2);
		DiagramTreeNode tree3 = new DiagramTreeNode(diagram3);
		
		DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("dato1");
		DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("dato2");
		DefaultMutableTreeNode child4 = new DefaultMutableTreeNode("dato3");
		
		Object[] path = {tree1, child1, child2, child4, tree2, tree3};
		
		ProjectController controller = this.createController();
		controller.changeElement(new TreePath(path));
		
		List<Diagram> diagrams = this.projectContext.getContextDiagrams();
		
		Assert.assertEquals(3, diagrams.size());
		
		Assert.assertSame(diagram1, diagrams.get(0));
		Assert.assertSame(diagram2, diagrams.get(1));
		Assert.assertSame(diagram3, diagrams.get(2));
	}
	
	private DefaultMutableTreeNode getNodeChildWithObject(DefaultMutableTreeNode node, String childName, Object object) {	
		Enumeration children = node.children();
		while (children.hasMoreElements()) {
		  DefaultMutableTreeNode element = (DefaultMutableTreeNode)children.nextElement();
		  if (String.class.isInstance(element.getUserObject())){
			  String value = (String)element.getUserObject();
			  if (value.equalsIgnoreCase(childName)){
				  Enumeration grandChildren = element.children();
				  while (grandChildren.hasMoreElements()) {
					  element = (DefaultMutableTreeNode)grandChildren.nextElement();
					  if (element.getUserObject() == object){
						  return element;
					  }
				  }
			  }
		  }
		}
		
		return null;
	}

	private void deleteFile(String name){
		File file = new File(name);
		file.delete();
	}
	
	private boolean fileExists(String name){
		File file = new File(name);
		return file.exists();
	}
	
	private ProjectController createController(){
		return new ProjectController(this.projectContext, this.projectView, this.shell, this.diagramControllerFactory);
	}
}
