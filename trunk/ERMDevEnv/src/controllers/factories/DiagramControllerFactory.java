package controllers.factories;

import org.picocontainer.MutablePicoContainer;

import application.tests.mocks.MockPicoContainer;
import controllers.IDiagramController;

public class DiagramControllerFactory implements IDiagramControllerFactory {

	private MutablePicoContainer container;

	public DiagramControllerFactory(MutablePicoContainer container) {
		this.container = container;
	}

	@Override
	public IDiagramController create() {
		return this.container.getComponent(IDiagramController.class);
	}

}
