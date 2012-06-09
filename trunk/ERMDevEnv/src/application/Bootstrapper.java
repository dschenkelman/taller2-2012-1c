package application;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import controllers.factories.*;
import controllers.factories.mock.MockEntityControllerFactory;
import controllers.factories.mock.MockHierarchyControllerFactory;
import infrastructure.IProjectContext;
import infrastructure.ProjectContext;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.shape.mxIMarker;
import com.mxgraph.view.mxCellState;
import com.mxgraph.shape.mxMarkerRegistry;
import com.mxgraph.util.mxPoint;

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

public class Bootstrapper {

	private MutablePicoContainer container;

	public MutablePicoContainer getContainer() {
		return this.container;
	}

	public void run() {
		this.container = this.createContainer();
		this.configureContainer();
		this.registerJGraphExtensions();
	}

	private void registerJGraphExtensions() {
		mxMarkerRegistry.registerMarker("emptyCircle", new mxIMarker()
		{
			public mxPoint paintMarker(mxGraphics2DCanvas canvas,
					mxCellState state, String type, mxPoint pe, double nx,
					double ny, double size)
			{
				double cx = pe.getX() - nx / 2;
				double cy = pe.getY() - ny / 2;
				double a = size / 2;
				Shape shape = new Ellipse2D.Double(cx - a, cy - a, size, size);

				//canvas.fillShape(shape);
				canvas.getGraphics().draw(shape);

				return new mxPoint(-nx / 2, -ny / 2);
			}
		});
	}

	private void configureContainer() {
		this.container
					.addComponent(MutablePicoContainer.class, this.container)
					.addComponent(IDiagramController.class, DiagramController.class)
					.addComponent(IDiagramView.class, DiagramView.class)
					.addComponent(IXmlManager.class, DiagramXmlManager.class)
					.addComponent(IXmlFileManager.class, XmlFileManager.class)
					.addComponent(IProjectContext.class, ProjectContext.class)
					.addComponent(IEntityControllerFactory.class, MockEntityControllerFactory.class)
					.addComponent(IRelationshipControllerFactory.class, RelationshipControllerFactory.class)
					.addComponent(IHierarchyControllerFactory.class, MockHierarchyControllerFactory.class)
					.addComponent(IStrongEntityControllerFactory.class, StrongEntityControllerFactory.class)
					.addComponent(IKeysControllerFactory.class, KeyControllerFactory.class)
					.addComponent(IGraphPersistenceService.class, GraphPersistenceService.class)
					.addComponent(IAttributeControllerFactory.class, AttributeControllerFactory.class);
	}

	public MutablePicoContainer createContainer() {
		return new DefaultPicoContainer();
	}

}
