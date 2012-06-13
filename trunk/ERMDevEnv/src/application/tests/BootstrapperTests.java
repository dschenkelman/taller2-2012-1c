package application.tests;

import controllers.factories.*;
import controllers.factories.mock.MockEntityControllerFactory;
import controllers.factories.mock.MockHierarchyControllerFactory;
import infrastructure.IProjectContext;
import infrastructure.ProjectContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;
import persistence.DiagramXmlManager;
import persistence.GraphPersistenceService;
import persistence.IGraphPersistenceService;
import persistence.IXmlFileManager;
import persistence.IXmlManager;
import persistence.XmlFileManager;
import views.DiagramView;
import views.IDiagramView;
import controllers.DiagramController;
import controllers.IDiagramController;
import controllers.IProjectController;
import controllers.ProjectController;

import application.Bootstrapper;
import application.tests.mocks.MockPicoContainer;

public class BootstrapperTests {
	
	private MockPicoContainer container;

	@Before
	public void setUp() throws Exception {
		this.container = new MockPicoContainer();
	}
	
	@Test
	public void testShouldCreateContainerWhenRunning(){
		TestableBootstrapper bootstrapper = this.createBootstrapper();
		
		bootstrapper.setContainerToReturn(this.container);
		
		Assert.assertNull(bootstrapper.getContainer());
		
		bootstrapper.run();
		
		Assert.assertNotNull(bootstrapper.getContainer());
		junit.framework.Assert.assertSame(this.container, bootstrapper.getContainer());
	}
	
	@Test
	public void testShouldConfigureMappings(){
		TestableBootstrapper bootstrapper = this.createBootstrapper();
		bootstrapper.setContainerToReturn(this.container);
		
		Assert.assertEquals(0, this.container.getMappings().size());
		
		bootstrapper.run();
		
		Assert.assertEquals(15, this.container.getMappings().size());
		Assert.assertSame(this.container, this.container.getMappings().get(MutablePicoContainer.class));
		Assert.assertSame(DiagramController.class, this.container.getMappings().get(IDiagramController.class));
		Assert.assertSame(DiagramView.class, this.container.getMappings().get(IDiagramView.class));
		Assert.assertSame(DiagramXmlManager.class, this.container.getMappings().get(IXmlManager.class));
		Assert.assertSame(XmlFileManager.class, this.container.getMappings().get(IXmlFileManager.class));
		Assert.assertSame(ProjectContext.class, this.container.getMappings().get(IProjectContext.class));
		Assert.assertSame(MockEntityControllerFactory.class, this.container.getMappings().get(IEntityControllerFactory.class));
		Assert.assertSame(RelationshipControllerFactory.class, this.container.getMappings().get(IRelationshipControllerFactory.class));
		Assert.assertSame(MockHierarchyControllerFactory.class, this.container.getMappings().get(IHierarchyControllerFactory.class));
		Assert.assertSame(StrongEntityControllerFactory.class, this.container.getMappings().get(IStrongEntityControllerFactory.class));
		Assert.assertSame(AttributeControllerFactory.class, this.container.getMappings().get(IAttributeControllerFactory.class));
		Assert.assertSame(KeyControllerFactory.class, this.container.getMappings().get(IKeysControllerFactory.class));
		Assert.assertSame(GraphPersistenceService.class, this.container.getMappings().get(IGraphPersistenceService.class));
		Assert.assertSame(DiagramControllerFactory.class, this.container.getMappings().get(IDiagramControllerFactory.class));
		Assert.assertSame(ProjectController.class, this.container.getMappings().get(IProjectController.class));
	}
	
	private TestableBootstrapper createBootstrapper(){
		return new TestableBootstrapper();
	}
	
	private class TestableBootstrapper extends Bootstrapper	{
		private MutablePicoContainer containerToReturn;
		
		public void setContainerToReturn(MutablePicoContainer c){
			this.containerToReturn = c;
		}
		
		@Override
		public MutablePicoContainer createContainer(){
			return this.containerToReturn;
		};
	}
}