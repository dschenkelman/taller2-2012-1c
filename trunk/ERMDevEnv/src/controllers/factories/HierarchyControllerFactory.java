package controllers.factories;

import infrastructure.IProjectContext;

import org.picocontainer.MutablePicoContainer;

import views.HierarchyView;

import application.Bootstrapper;
import models.Hierarchy;

import controllers.HierarchyController;
import controllers.IHierarchyController;

public class HierarchyControllerFactory implements IHierarchyControllerFactory {

	@Override
	public IHierarchyController create() {
		return this.create(new Hierarchy());
	}

	@Override
	public IHierarchyController create(Hierarchy hierarchy) {
		Bootstrapper bootstrapper = new Bootstrapper();
		bootstrapper.run();
		MutablePicoContainer container = bootstrapper.getContainer();
		return new HierarchyController(container.getComponent(IProjectContext.class), hierarchy, new HierarchyView());
	}
	
	

}
