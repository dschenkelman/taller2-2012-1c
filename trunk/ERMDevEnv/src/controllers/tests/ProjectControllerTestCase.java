package controllers.tests;


import java.io.File;
import java.util.UUID;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.IProjectController;
import controllers.ProjectController;
import controllers.factories.tests.mocks.MockDiagramControllerFactory;
import controllers.tests.mocks.MockDiagramController;
import controllers.tests.mocks.MockProjectContext;
import controllers.tests.mocks.MockProjectView;
import controllers.tests.mocks.MockShell;

public class ProjectControllerTestCase {

	private MockProjectContext projectContext;
	
	private MockDiagramControllerFactory diagramControllerFactory;

	private MockDiagramController diagramController;
	
	private MockProjectView projectView;
	
	private MockShell shell;
	
	@Before
	public void setUp() throws Exception {
		this.projectContext = new MockProjectContext();
		this.diagramControllerFactory = new MockDiagramControllerFactory();
		this.diagramController = new MockDiagramController();
		this.diagramControllerFactory.setController(this.diagramController);
		this.projectView = new MockProjectView();
		this.shell = new MockShell();
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
