package application;

import infrastructure.IProjectContext;
import infrastructure.ProjectContext;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import persistence.DiagramXmlManager;
import persistence.IXmlFileManager;
import persistence.IXmlManager;
import persistence.XmlFileManager;
import views.DiagramView;
import views.IDiagramView;
import controllers.DiagramController;
import controllers.IDiagramController;
import controllers.factories.EntityControllerFactory;
import controllers.factories.IEntityControllerFactory;
import controllers.factories.IRelationshipControllerFactory;
import controllers.factories.RelationshipControllerFactory;

public class Bootstrapper {

	private MutablePicoContainer container;

	public MutablePicoContainer getContainer() {
		return this.container;
	}

	public void run() {
		this.container = this.createContainer();
		this.configureContainer();
	}

	private void configureContainer() {
		this.container
					.addComponent(MutablePicoContainer.class, this.container)
					.addComponent(IDiagramController.class, DiagramController.class)
					.addComponent(IDiagramView.class, DiagramView.class)
					.addComponent(IXmlManager.class, DiagramXmlManager.class)
					.addComponent(IXmlFileManager.class, XmlFileManager.class)
					.addComponent(IProjectContext.class, ProjectContext.class)
					.addComponent(IEntityControllerFactory.class, EntityControllerFactory.class)
					.addComponent(IRelationshipControllerFactory.class, RelationshipControllerFactory.class);
	}

	public MutablePicoContainer createContainer() {
		return new DefaultPicoContainer();
	}

}
