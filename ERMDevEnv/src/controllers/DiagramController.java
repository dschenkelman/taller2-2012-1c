package controllers;

import infrastructure.IControllerFactory;

import java.util.HashMap;
import java.util.Map;

import models.Attribute;
import models.Entity;
import models.Relationship;

import jgraph.extensions.CustomGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import views.IDiagramView;

public class DiagramController extends BaseController 
	implements IDiagramController{

	private CustomGraph graph;
	private Map<String, mxCell> entityCells;
	private Map<String, mxCell> attributeCells;
	private Map<String, mxCell> attributeConnectorCells;
	private IControllerFactory<IEntityController, Entity> entityControllerFactory;
	private Entity pendingEntity;
	private IControllerFactory<IRelationshipController, Relationship> relationshipControllerFactory;

	public DiagramController(IProjectContext projectContext, IDiagramView diagramView, 
			IControllerFactory<IEntityController, Entity> entityControllerFactory,
			IControllerFactory<IRelationshipController, Relationship> relationshipControllerFactory) {
		super(projectContext);
		this.entityControllerFactory = entityControllerFactory;
		this.relationshipControllerFactory = relationshipControllerFactory;
		this.graph = new CustomGraph();
		this.entityCells = new HashMap<String, mxCell>();
		this.attributeCells = new HashMap<String, mxCell>();
		this.attributeConnectorCells = new HashMap<String, mxCell>();
		diagramView.setController(this);
		
	}

	public mxGraph getGraph() {
		return this.graph;
	}

	public void createEntity() {
		if (!this.hasPendingEntity())
		{
			IEntityController entityController = this.entityControllerFactory.create();
			entityController.addSubscriber(this);
			entityController.create();
		}
	}
	
    @Override
    public void handleCreatedEvent(Entity entity) {
        this.pendingEntity = entity;
    }

	public void createRelationship() {
		IRelationshipController relationshipController = 
			this.relationshipControllerFactory.create();
		
		relationshipController.addCreateListener(this);
		
		relationshipController.create();
	}

	@Override
	public void handleCreatedEvent(Relationship relationship) {
		
	}

	public void addEntity(double x, double y) 
	{
		this.graph.getModel().beginUpdate();
		Object parent = this.graph.getDefaultParent();
		try {
			mxCell entityCell = this.addEntityToGraph(this.pendingEntity, parent, x, y);
			
			for (Attribute attribute : this.pendingEntity.getAttributes()) {
				mxCell attributeCell = this.addAttributeToGraph(attribute, parent, this.pendingEntity);
				boolean isKey = attribute.isKey();
				this.addAttributeConnectorToGraph(parent, this.pendingEntity, entityCell, attribute, attributeCell, isKey);
			}
		}
		finally {
			this.graph.getModel().endUpdate();
		}
		
		this.pendingEntity = null;
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

	private mxCell addEntityToGraph(Entity entity, Object parent, double x, double y) {
		mxCell entityCell = (mxCell) this.graph.insertVertex(parent, entity.getId().toString(), 
				entity.getName(), x, y,
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

	public boolean hasPendingEntity() {
		return this.pendingEntity != null;
	}
}
