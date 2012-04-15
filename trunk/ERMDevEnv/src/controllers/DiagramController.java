package controllers;

import infrastructure.IControllerFactory;

import java.util.HashMap;
import java.util.Map;

import models.Attribute;
import models.Entity;

import jgraph.extensions.CustomGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import views.IDiagramView;

public class DiagramController extends BaseController implements IDiagramController {

	private CustomGraph graph;
	private Map<String, mxCell> entityCells;
	private Map<String, mxCell> attributeCells;
	private Map<String, mxCell> attributeConnectorCells;
	private IControllerFactory<IEntityController, Entity> entityControllerFactory;

	public DiagramController(IProjectContext projectContext, IDiagramView diagramView, 
			IControllerFactory<IEntityController, Entity> entityControllerFactory) {
		super(projectContext);
		this.entityControllerFactory = entityControllerFactory;
		this.graph = new CustomGraph();
		this.entityCells = new HashMap<String, mxCell>();
		this.attributeCells = new HashMap<String, mxCell>();
		this.attributeConnectorCells = new HashMap<String, mxCell>();
		diagramView.setController(this);
		
	}

	public mxGraph getGraph() {
		return this.graph;
	}

	public void addEntity() {
		IEntityController entityController = this.entityControllerFactory.create();
		Entity entity = entityController.create();
		
		this.graph.getModel().beginUpdate();
		Object parent = this.graph.getDefaultParent();
		try {
			mxCell entityCell = this.addEntityToGraph(entity, parent);
			
			for (Attribute attribute : entity.getAttributes()) {
				mxCell attributeCell = this.addAttributeToGraph(attribute, parent, entity);
				boolean isKey = attribute.isKey();
				this.addAttributeConnectorToGraph(parent, entity, entityCell, attribute, attributeCell, isKey);
			}
		}
		finally {
			this.graph.getModel().endUpdate();
		}
	}

	private mxCell addAttributeConnectorToGraph(Object parent, Entity entity, mxCell entityCell,
			Attribute attribute, mxCell attributeCell, boolean isKey) { 
		String attributeConnectorId = entity.getId().toString()+attribute.getName()+"AttributeConnector";
		
		mxCell connectorCell = (mxCell) this.graph.insertEdge(parent, attributeConnectorId, "", 
				entityCell, attributeCell, StyleConstants.ATTRIBUTE_LINK_STYLE);
		
		this.attributeConnectorCells.put(attributeConnectorId, connectorCell);
		
		return connectorCell;		
	}

	private mxCell addAttributeToGraph(Attribute attribute, Object parent, Entity entity) {
		String attributeId = entity.getId().toString()+attribute.getName();
		mxCell attributeCell = (mxCell) this.graph.insertVertex(parent, attributeId, 
				attribute.getName(), 0, 0,
				StyleConstants.ATTRIBUTE_WIDTH, StyleConstants.ATTRIBUTE_HEIGHT);
		
		this.attributeCells.put(attributeId, attributeCell);

		return attributeCell;
	}

	private mxCell addEntityToGraph(Entity entity, Object parent) {
		mxCell entityCell = (mxCell) this.graph.insertVertex(parent, entity.getId().toString(), entity.getName(), 0, 0,
				StyleConstants.ENTITY_WIDTH, StyleConstants.ENTITY_HEIGHT);
		
		this.entityCells.put(entity.getId().toString(), entityCell);

		return entityCell;
	}

	public mxCell getEntityCell(String id) {
		return this.entityCells.get(id);
	}

	public mxCell getAttributeCell(String id) {
		return this.attributeCells.get(id);
	}

	public mxCell getAttributeConnectorCell(String id) {
		return this.attributeConnectorCells.get(id);
	}

}
