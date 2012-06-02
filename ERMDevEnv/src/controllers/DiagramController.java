package controllers;

import infrastructure.IProjectContext;
import infrastructure.IterableExtensions;
import infrastructure.StringExtensions;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IXmlFileManager;
import persistence.IXmlManager;
import styling.StyleConstants;
import styling.Styler;

import models.Attribute;
import models.Cardinality;
import models.Diagram;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import jgraph.extensions.CustomGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import controllers.factories.IEntityControllerFactory;
import controllers.factories.IRelationshipControllerFactory;

import views.IDiagramView;

public class DiagramController extends BaseController 
	implements IDiagramController{

	private CustomGraph graph;
	private Map<String, mxCell> entityCells;
	private Map<String, mxCell> relationshipCells;
	private Map<String, mxCell> attributeCells;
	private Map<String, mxCell> attributeConnectorCells;
	private Map<String, mxCell> relationshipConnectorCells;
	private IEntityControllerFactory entityControllerFactory;
	private Entity pendingEntity;
	private IRelationshipControllerFactory relationshipControllerFactory;
	private Diagram diagram;
	private IXmlFileManager xmlFileManager;
	private IXmlManager<Diagram> diagramXmlManager;
	private IDiagramView diagramView;
	
	public DiagramController(IProjectContext projectContext, IDiagramView diagramView, 
			IEntityControllerFactory entityControllerFactory,
			IRelationshipControllerFactory relationshipControllerFactory,
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
		this.diagramView = diagramView;
		this.diagramView.setController(this);
	}
	
	public IDiagramView getView(){
		return this.diagramView;
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
		double[] coordinates = this.getRelationshipNodeCoordinates(relationship.getRelationshipEntities());
		double x = coordinates[0];
		double y = coordinates[1];
		this.graph.getModel().beginUpdate();
		
		try{
			Object parent = this.graph.getDefaultParent();
			mxCell relationshipCell = this.addRelationshipToGraph(relationship, parent, x, y);
			
			double centerX = relationshipCell.getGeometry().getCenterX();
			double centerY = relationshipCell.getGeometry().getCenterY();
			
			int attributeCount = relationship.getAttributes().count();
			double partialAngle = (2 * Math.PI) / attributeCount != 0 ? attributeCount : 0;
			double currentAngle = 0;
			
			for (RelationshipEntity relationshipEntity : relationship.getRelationshipEntities()) {
				this.addRelationshipConnectorToGraph(parent, relationship, relationshipCell, relationshipEntity);
			}
			
			for (Attribute attribute : relationship.getAttributes()) {
				double xDistance = Math.cos(currentAngle) * StyleConstants.ATTRIBUTE_DEFAULT_DISTANCE;
				double yDistance = Math.sin(currentAngle) * StyleConstants.ATTRIBUTE_DEFAULT_DISTANCE;
				
				double attributeX = centerX + xDistance;
				double attributeY = centerY + yDistance;
				
				mxCell attributeCell = this.addAttributeToGraph(attribute, parent, relationship.getId(), attributeX, attributeY);
				
				boolean isKey = attribute.isKey();
				this.addAttributeConnectorToGraph(parent, relationship.getId(), relationshipCell, attribute, attributeCell, isKey);
				
				currentAngle += partialAngle;
			}
		}
		finally {
			this.diagram.getRelationships().add(relationship);
			this.graph.getModel().endUpdate();
		}
	}
	
	public void addEntity(double x, double y) throws Exception 
	{
		this.graph.getModel().beginUpdate();
		Object parent = this.graph.getDefaultParent();
		try {
			mxCell entityCell = this.addEntityToGraph(this.pendingEntity, parent, x, y);
			double centerX = entityCell.getGeometry().getCenterX();
			double centerY = entityCell.getGeometry().getCenterY();
			
			int attributeCount = this.pendingEntity.getAttributes().count();
			double partialAngle = (2 * Math.PI) / attributeCount != 0 ? attributeCount : 0;
			double currentAngle = 0;
			
			for (Attribute attribute : this.pendingEntity.getAttributes()) {
				double xDistance = Math.cos(currentAngle) * StyleConstants.ATTRIBUTE_DEFAULT_DISTANCE;
				double yDistance = Math.sin(currentAngle) * StyleConstants.ATTRIBUTE_DEFAULT_DISTANCE;
				
				double attributeX = centerX + xDistance;
				double attributeY = centerY + yDistance;
				
				mxCell attributeCell = this.addAttributeToGraph(attribute, parent, this.pendingEntity.getId(), attributeX, attributeY);
				boolean isKey = attribute.isKey();
				this.addAttributeConnectorToGraph(parent, this.pendingEntity.getId(), entityCell, attribute, attributeCell, isKey);
				currentAngle += partialAngle;
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

	private mxCell addAttributeConnectorToGraph(Object parent, UUID ownerId, mxCell entityCell,
			Attribute attribute, mxCell attributeCell, boolean isKey) { 
		String attributeConnectorId = ownerId.toString()+attribute.getName()+"AttributeConnector";
		
		mxCell connectorCell = (mxCell) this.graph.insertEdge(parent, attributeConnectorId, "", 
				entityCell, attributeCell, StyleConstants.ATTRIBUTE_LINK_STYLE);
		
		this.attributeConnectorCells.put(attributeConnectorId, connectorCell);
		
		return connectorCell;		
	}

	private mxCell addAttributeToGraph(Attribute attribute, Object parent, UUID ownerId, double x, double y) {
		String attributeId = ownerId.toString()+attribute.getName();
		mxCell attributeCell = (mxCell) this.graph.insertVertex(parent, attributeId, 
				attribute.getName(), x, y,
				StyleConstants.ATTRIBUTE_WIDTH, StyleConstants.ATTRIBUTE_HEIGHT);
		
		this.attributeCells.put(attributeId, attributeCell);

		return attributeCell;
	}
	
	private mxCell addRelationshipToGraph(Relationship relationship, Object parent, double x, double y) {
		mxCell relationshipCell = (mxCell) this.graph.insertVertex(parent, relationship.getId().toString(), 
				relationship.getName(), x, y,
				StyleConstants.RELATIONSHIP_WIDTH, StyleConstants.RELATIONSHIP_HEIGHT, StyleConstants.RELATIONSHIP_STYLE);
		
		this.relationshipCells.put(relationship.getId().toString(), relationshipCell);

		return relationshipCell;
	}

	private mxCell addEntityToGraph(Entity entity, Object parent, double x, double y) throws Exception {
		mxCell entityCell = (mxCell) this.graph.insertVertex(parent, entity.getId().toString(), 
				entity.getName(), x, y,
				StyleConstants.ENTITY_WIDTH, StyleConstants.ENTITY_HEIGHT, Styler.getFillColor(entity.getType()));
		
		this.entityCells.put(entity.getId().toString(), entityCell);

		return entityCell;
	}
	
	private double[] getRelationshipNodeCoordinates(Iterable<RelationshipEntity> relationshipEntities) {
		double maxX = 0;
		double maxY = 0;
		double minY = Double.POSITIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY;
		
		Iterable<RelationshipEntity> distinctEntities = IterableExtensions.distinct(relationshipEntities, new Comparator<RelationshipEntity>() {

			@Override
			public int compare(RelationshipEntity e1, RelationshipEntity e2) {
				if (e1.getEntityId() == e2.getEntityId())
				{
					return 0;
				}
				
				return 1;
			}
		});
		
		if (IterableExtensions.count(distinctEntities) == 1)
		{
			RelationshipEntity entity = IterableExtensions.firstOrDefault(distinctEntities);
			
			mxCell entityCell = this.getEntityCell(entity.getEntityId().toString());
			double x = entityCell.getGeometry().getX();
			double y = entityCell.getGeometry().getY();
			
			return new double[]{x, y + StyleConstants.ENTITY_HEIGHT * 3};
		}
		
		for (RelationshipEntity entity : distinctEntities) {
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
	
	public void openDiagram(String path) throws Exception {
		Document document = this.xmlFileManager.read(path);
		Element element = document.getDocumentElement();
		this.diagram = this.diagramXmlManager.getItemFromXmlElement(element);
	}
}
