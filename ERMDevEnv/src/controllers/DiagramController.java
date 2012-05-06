package controllers;

import infrastructure.IControllerFactory;
import infrastructure.StringExtensions;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlFileManager;
import persistence.IXmlManager;

import models.Attribute;
import models.Cardinality;
import models.Diagram;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import jgraph.extensions.CustomGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import views.IDiagramView;

public class DiagramController extends BaseController 
	implements IDiagramController{

	private CustomGraph graph;
	private Map<String, mxCell> entityCells;
	private Map<String, mxCell> relationshipCells;
	private Map<String, mxCell> attributeCells;
	private Map<String, mxCell> attributeConnectorCells;
	private Map<String, mxCell> relationshipConnectorCells;
	private IControllerFactory<IEntityController, Entity> entityControllerFactory;
	private Entity pendingEntity;
	private IControllerFactory<IRelationshipController, Relationship> relationshipControllerFactory;
	private Diagram diagram;
	private IXmlFileManager xmlFileManager;
	private IXmlManager<Diagram> diagramXmlManager;
	
	public DiagramController(IProjectContext projectContext, IDiagramView diagramView, 
			IControllerFactory<IEntityController, Entity> entityControllerFactory,
			IControllerFactory<IRelationshipController, Relationship> relationshipControllerFactory,
			IXmlFileManager xmlFileManager,
			IXmlManager<Diagram> diagramXmlManager) {
		super(projectContext);
		this.diagram = new Diagram();
		this.entityControllerFactory = entityControllerFactory;
		this.relationshipControllerFactory = relationshipControllerFactory;
		this.graph = new CustomGraph();
		this.entityCells = new HashMap<String, mxCell>();
		this.attributeCells = new HashMap<String, mxCell>();
		this.attributeConnectorCells = new HashMap<String, mxCell>();
		this.relationshipCells = new HashMap<String, mxCell>();
		this.relationshipConnectorCells = new HashMap<String, mxCell>();
		this.xmlFileManager = xmlFileManager;
		this.diagramXmlManager = diagramXmlManager;
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
		double[] coordinates = this.getCoordinates(relationship.getRelationshipEntities());
		double x = coordinates[0];
		double y = coordinates[1];
		this.graph.getModel().beginUpdate();
		
		try{
			Object parent = this.graph.getDefaultParent();
			mxCell relationshipCell = this.addRelationshipToGraph(relationship, parent, x, y);
			
			for (RelationshipEntity relationshipEntity : relationship.getRelationshipEntities()) {
				this.addRelationshipConnectorToGraph(parent, relationship, relationshipCell, relationshipEntity);
			}
		}
		finally {
			this.diagram.getRelationships().add(relationship);
			this.graph.getModel().endUpdate();
		}
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
			this.diagram.getEntities().add(this.pendingEntity);
			this.graph.getModel().endUpdate();
		}
		
		this.pendingEntity = null;
	}

	private mxCell addRelationshipConnectorToGraph(Object parent, Relationship relationship, mxCell relationshipCell,
			RelationshipEntity relationshipEntity) {
		String cardinalityDisplay = String.format("(%s,%s)", 
				Cardinality.getStringForCardinality(relationshipEntity
						.getCardinality().getMinimum()),
				Cardinality.getStringForCardinality(relationshipEntity
						.getCardinality().getMaximum()));
		
		String displayValue = StringExtensions.isNullOrEmpty(relationshipEntity.getRole()) ? 
				cardinalityDisplay
				: String.format("%s %s", relationshipEntity.getRole(), cardinalityDisplay);
		
		String connectorId = relationship.getId().toString() + 
			relationshipEntity.getEntityId().toString() + 
			relationshipEntity.getRole();
		
		mxCell entityCell = this.getEntityCell(relationshipEntity.getEntityId().toString());
		
		mxCell connectorCell = (mxCell) this.graph.insertEdge(parent, connectorId, displayValue, 
				relationshipCell, entityCell, StyleConstants.RELATIONSHIP_CONNECTOR_STYLE);
		
		this.relationshipConnectorCells.put(connectorId, connectorCell);
		
		return connectorCell;
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
	
	private mxCell addRelationshipToGraph(Relationship relationship, Object parent, double x, double y) {
		mxCell relationshipCell = (mxCell) this.graph.insertVertex(parent, relationship.getId().toString(), 
				relationship.getName(), x, y,
				StyleConstants.ENTITY_WIDTH, StyleConstants.ENTITY_HEIGHT);
		
		this.relationshipCells.put(relationship.getId().toString(), relationshipCell);

		return relationshipCell;
	}

	private mxCell addEntityToGraph(Entity entity, Object parent, double x, double y) {
		mxCell entityCell = (mxCell) this.graph.insertVertex(parent, entity.getId().toString(), 
				entity.getName(), x, y,
				StyleConstants.ENTITY_WIDTH, StyleConstants.ENTITY_HEIGHT);
		
		this.entityCells.put(entity.getId().toString(), entityCell);

		return entityCell;
	}
	
	private double[] getCoordinates(Iterable<RelationshipEntity> relationshipEntities) {
		double maxX = 0;
		double maxY = 0;
		double minY = Double.POSITIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY;
		
		for (RelationshipEntity entity : relationshipEntities) {
			mxCell entityCell = this.getEntityCell(entity.getEntityId().toString());
			double x = entityCell.getGeometry().getX();
			double y = entityCell.getGeometry().getY();
			if (x > maxX){
				maxX = x;
			}
			
			if (x < minX){
				minX = x;
			}
			
			if (y > maxY){
				maxY = y;
			}
			
			if (y < minY){
				minY = y;
			}
		}
		
		double xPosition = minX + (maxX - minX) / 2;
		double yPosition = minY + (maxY - minY) / 2;
		
		return new double[]{xPosition, yPosition};
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

	public mxCell getRelationshipCell(String id) {
		return this.relationshipCells.get(id);
	}

	
	public mxCell getRelationshipConnectorCell(String id) {
		return this.relationshipConnectorCells.get(id);
	}

	public Diagram getDiagram() {
		return this.diagram;
	}

	public void save() throws ParserConfigurationException {
		Document document = this.xmlFileManager.createDocument();
		Element element = 
			this.diagramXmlManager.getElementFromItem(this.diagram, document);
		
		document.appendChild(element);
		this.xmlFileManager.write(document, this.diagram.getName() + "-comp");
		
		//this.xmlFileManager.write(, filePath)
	}
}
