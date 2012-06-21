package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import controllers.factories.*;
import controllers.factories.mock.MockEntityControllerFactory;
import controllers.factories.mock.MockHierarchyControllerFactory;
import infrastructure.IProjectContext;
import infrastructure.ProjectContext;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import static org.picocontainer.Characteristics.CACHE;

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
import views.HierarchyView;
import views.IDiagramView;
import views.IHierarchyView;
import views.IProjectView;
import views.ProjectView;
import controllers.DiagramController;
import controllers.HierarchyController;
import controllers.IDiagramController;
import controllers.IHierarchyController;
import controllers.IProjectController;
import controllers.ProjectController;

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
				
				canvas.getGraphics().draw(shape);

				return new mxPoint(-nx / 2, -ny / 2);
			}
		});
		mxMarkerRegistry.registerMarker("emptyRedCircle", new mxIMarker()
		{
			public mxPoint paintMarker(mxGraphics2DCanvas canvas,
					mxCellState state, String type, mxPoint pe, double nx,
					double ny, double size)
			{
				double cx = pe.getX() - nx / 2;
				double cy = pe.getY() - ny / 2;
				double a = size / 2;
				Shape shape = new Ellipse2D.Double(cx - a, cy - a, size, size);
				
				Color originalColor = canvas.getGraphics().getColor();
				canvas.getGraphics().setColor(Color.RED);
				canvas.getGraphics().draw(shape);
				canvas.getGraphics().setColor(originalColor);

				return new mxPoint(-nx / 2, -ny / 2);
			}
		});
	}

	private void configureContainer() {
		this.container
					.as(CACHE).addComponent(MutablePicoContainer.class, this.container)
					.addComponent(IDiagramController.class, DiagramController.class)
					.addComponent(IDiagramView.class, DiagramView.class)
					.addComponent(IXmlManager.class, DiagramXmlManager.class)
					.as(CACHE).addComponent(IXmlFileManager.class, XmlFileManager.class)
					.as(CACHE).addComponent(IProjectContext.class, ProjectContext.class)
					.as(CACHE).addComponent(IEntityControllerFactory.class, EntityControllerFactory.class)
					.as(CACHE).addComponent(IRelationshipControllerFactory.class, RelationshipControllerFactory.class)
					.as(CACHE).addComponent(IHierarchyControllerFactory.class, HierarchyControllerFactory.class)
					.addComponent(IHierarchyController.class, HierarchyController.class)
					.addComponent(IHierarchyView.class, HierarchyView.class)
					.as(CACHE).addComponent(IKeysControllerFactory.class, KeyControllerFactory.class)
					.as(CACHE).addComponent(IGraphPersistenceService.class, GraphPersistenceService.class)
					.as(CACHE).addComponent(IDiagramControllerFactory.class, DiagramControllerFactory.class)
					.addComponent(IProjectController.class, ProjectController.class)
					.addComponent(IProjectView.class, ProjectView.class)
					.as(CACHE).addComponent(IAttributeControllerFactory.class, AttributeControllerFactory.class);
	}

	public MutablePicoContainer createContainer() {
		return new DefaultPicoContainer();
	}

}
