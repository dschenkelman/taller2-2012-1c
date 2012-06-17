package controllers;

import infrastructure.IProjectContext;
import infrastructure.IterableExtensions;
import infrastructure.StringExtensions;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.IGraphPersistenceService;
import persistence.IXmlFileManager;
import persistence.IXmlManager;
import styling.StyleConstants;
import styling.Styler;

import models.Attribute;
import models.Cardinality;
import models.Diagram;
import models.Entity;
import models.Hierarchy;
import models.Relationship;
import models.RelationshipEntity;

import jgraph.extensions.CustomGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

import controllers.factories.IEntityControllerFactory;
import controllers.factories.IHierarchyControllerFactory;
import controllers.factories.IRelationshipControllerFactory;
import controllers.listeners.IDiagramEventListener;
import controllers.tests.mocks.MockGraphPersistenceService;

import views.IDiagramView;

public class DiagramController extends BaseController 
	implements IDiagramController, mxIEventListener{

	private static class CellConstants{
		public static String EntityPrefix = "Entity";
		public static String RelationshipPrefix = "Relationship";
		public static String AttributePrefix = "Attribute";
		public static String AttributeConnectorPrefix = "AttributeConnector";
		public static String RelationshipConnectorPrefix = "RelationshipConnector";
		public static String HierarchyNodePrefix = "HierarchyNode";
		public static String HierarchyConnectorPrefix = "HierarchyConnector";
	}
	
	private CustomGraph graph;
	private Map<String, mxCell> entityCells;
	private Map<String, mxCell> relationshipCells;
	private Map<String, mxCell> attributeCells;
	private Map<String, mxCell> attributeConnectorCells;
	private Map<String, mxCell> relationshipConnectorCells;
	private Map<String, mxCell> hierarchyNodeCells;
	private Map<String, mxCell> hierarchyConnectorCells;
	private IEntityControllerFactory entityControllerFactory;
	private Entity pendingEntity;
	private IRelationshipControllerFactory relationshipControllerFactory;
	private Diagram diagram;
	private IXmlFileManager xmlFileManager;
	private IXmlManager<Diagram> diagramXmlManager;
	private IDiagramView diagramView;
	private List<mxCell> selectedCells;
	private Point dragStartPoint;
	private IHierarchyControllerFactory hierarchyControllerFactory;
	private IGraphPersistenceService graphPersistenceService;
	private List<IDiagramEventListener> listeners;
	
	public DiagramController(IProjectContext projectContext, IDiagramView diagramView, 
			IEntityControllerFactory entityControllerFactory,
			IRelationshipControllerFactory relationshipControllerFactory,
			IHierarchyControllerFactory hierarchyControllerFactory,
			IXmlFileManager xmlFileManager,
			IXmlManager<Diagram> diagramXmlManager,
			IGraphPersistenceService graphPersistenceService) {
		super(projectContext);
		this.diagram = new Diagram();
		this.selectedCells = new ArrayList<mxCell>();
		this.entityControllerFactory = entityControllerFactory;
		this.relationshipControllerFactory = relationshipControllerFactory;
		this.hierarchyControllerFactory = hierarchyControllerFactory;
		this.graph = new CustomGraph();
						
		this.graph.getSelectionModel().addListener(mxEvent.CHANGE, this);
		
		this.entityCells = new HashMap<String, mxCell>();
		this.attributeCells = new HashMap<String, mxCell>();
		this.attributeConnectorCells = new HashMap<String, mxCell>();
		this.relationshipCells = new HashMap<String, mxCell>();
		this.relationshipConnectorCells = new HashMap<String, mxCell>();
		this.hierarchyConnectorCells = new HashMap<String, mxCell>();
		this.hierarchyNodeCells = new HashMap<String, mxCell>();
		this.xmlFileManager = xmlFileManager;
		this.diagramXmlManager = diagramXmlManager;
		this.diagramView = diagramView;
		this.diagramView.setController(this);
		this.graphPersistenceService = graphPersistenceService;
		this.listeners = new ArrayList<IDiagramEventListener>();
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
			
			Map<UUID, Integer> entityCount = new HashMap<UUID, Integer>();
			
			for (RelationshipEntity relationshipEntity : relationship.getRelationshipEntities()) {
				if (!entityCount.containsKey(relationshipEntity.getEntityId()))
				{
					entityCount.put(relationshipEntity.getEntityId(), 1);
				}
				else
				{
					Integer count = entityCount.get(relationshipEntity.getEntityId()); 
					count++;
					entityCount.put(relationshipEntity.getEntityId(), count);
				}
			}
			
			List<UUID> repeatedRelationships = new ArrayList<UUID>();
			int relationshipEntitiesCount = 0;
			for (UUID uuid : entityCount.keySet()) {
				Integer count = entityCount.get(uuid);
				if (count > 1){
					repeatedRelationships.add(uuid);
					relationshipEntitiesCount += count;
				}
			}
			
			double partialAttributeAngle = attributeCount != 0 ? (2 * Math.PI) / attributeCount : 0;
			double partialRelationshipEntitiesAngle = relationshipEntitiesCount != 0 ? (2 * Math.PI) / relationshipEntitiesCount : 0;
			double currentAttributeAngle = 0;
			double currentRelationshipEntitiesAngle = 0;
			
			for (RelationshipEntity relationshipEntity : relationship.getRelationshipEntities()) {
				
				double xExit = 0;
				double yExit = 0;
				Boolean useExit = false;
				if (repeatedRelationships.contains(relationshipEntity.getEntityId())){
					xExit = Math.cos(currentRelationshipEntitiesAngle) * 0.5 + 0.5;
					yExit = Math.sin(currentRelationshipEntitiesAngle) * 0.5 + 0.5;
					currentRelationshipEntitiesAngle += partialRelationshipEntitiesAngle;
					useExit = true;
				}
				
				this.addRelationshipConnectorToGraph(parent, relationship, relationshipCell, relationshipEntity, xExit, yExit, useExit);				
			}
			
			for (Attribute attribute : relationship.getAttributes()) {
				double xDistance = Math.cos(currentAttributeAngle) * StyleConstants.ATTRIBUTE_DEFAULT_DISTANCE;
				double yDistance = Math.sin(currentAttributeAngle) * StyleConstants.ATTRIBUTE_DEFAULT_DISTANCE;
				
				double attributeX = centerX + xDistance;
				double attributeY = centerY + yDistance;
				
				mxCell attributeCell = this.addAttributeToGraph(attribute, parent, relationship.getId(), attributeX, attributeY);
				
				boolean isKey = attribute.isKey();
				this.addAttributeConnectorToGraph(parent, relationship.getId(), relationshipCell, attribute, attributeCell, isKey);
				
				currentAttributeAngle += partialAttributeAngle;
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
			for (IDiagramEventListener listener : this.listeners) {
				listener.handleEntityAdded(this.diagram, this.pendingEntity);
			}
			this.graph.getModel().endUpdate();
		}
		
		this.pendingEntity = null;
	}

	private mxCell addRelationshipConnectorToGraph(Object parent, Relationship relationship, mxCell relationshipCell,
			RelationshipEntity relationshipEntity, double exitX, double exitY, Boolean useExit) {
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
		
		String exitStyle = "";
		
		if (useExit){
			exitStyle = ";" + Styler.getEdgeExitStyle(exitX, exitY);
		} 
		
		mxCell connectorCell = (mxCell) this.graph.insertEdge(parent, connectorId, displayValue, 
				relationshipCell, entityCell, StyleConstants.RELATIONSHIP_CONNECTOR_STYLE + exitStyle);
		
		this.relationshipConnectorCells.put(CellConstants.RelationshipConnectorPrefix + connectorId, connectorCell);
		
		return connectorCell;
	}

	private mxCell addAttributeConnectorToGraph(Object parent, UUID ownerId, mxCell entityCell,
			Attribute attribute, mxCell attributeCell, boolean isKey) { 
		String attributeConnectorId = ownerId.toString()+attribute.getName();
		
		mxCell connectorCell = (mxCell) this.graph.insertEdge(parent, attributeConnectorId, "", 
				entityCell, attributeCell, StyleConstants.ATTRIBUTE_LINK_STYLE);
		
		this.attributeConnectorCells.put(CellConstants.AttributeConnectorPrefix + attributeConnectorId, connectorCell);
		
		return connectorCell;		
	}

	private mxCell addAttributeToGraph(Attribute attribute, Object parent, UUID ownerId, double x, double y) {
		String attributeId = ownerId.toString()+attribute.getName();
		mxCell attributeCell = (mxCell) this.graph.insertVertex(parent, attributeId, 
				attribute.getName(), x, y,
				StyleConstants.ATTRIBUTE_WIDTH, StyleConstants.ATTRIBUTE_HEIGHT);
		
		this.attributeCells.put(CellConstants.AttributePrefix + attributeId, attributeCell);

		return attributeCell;
	}
	
	private mxCell addRelationshipToGraph(Relationship relationship, Object parent, double x, double y) {
		mxCell relationshipCell = (mxCell) this.graph.insertVertex(parent, relationship.getId().toString(), 
				relationship.getName(), x, y,
				StyleConstants.RELATIONSHIP_WIDTH, StyleConstants.RELATIONSHIP_HEIGHT, StyleConstants.RELATIONSHIP_STYLE);
		
		this.relationshipCells.put(CellConstants.RelationshipPrefix + relationship.getId().toString(), relationshipCell);

		return relationshipCell;
	}

	private mxCell addEntityToGraph(Entity entity, Object parent, double x, double y) throws Exception {
		mxCell entityCell = (mxCell) this.graph.insertVertex(parent, entity.getId().toString(), 
				entity.getName(), x, y,
				StyleConstants.ENTITY_WIDTH, StyleConstants.ENTITY_HEIGHT, Styler.getFillColor(entity.getType()));
		
		this.entityCells.put(CellConstants.EntityPrefix + entity.getId().toString(), entityCell);

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
		return this.entityCells.get(CellConstants.EntityPrefix + id);
	}

	public mxCell getAttributeCell(String id) {
		return this.attributeCells.get(CellConstants.AttributePrefix + id);
	}

	public mxCell getAttributeConnectorCell(String id) {
		return this.attributeConnectorCells.get(CellConstants.AttributeConnectorPrefix + id);
	}

	public boolean hasPendingEntity() {
		return this.pendingEntity != null;
	}

	public mxCell getRelationshipCell(String id) {
		return this.relationshipCells.get(CellConstants.RelationshipPrefix + id);
	}

	
	public mxCell getRelationshipConnectorCell(String id) {
		return this.relationshipConnectorCells.get(CellConstants.RelationshipConnectorPrefix + id);
	}

	public Diagram getDiagram() {
		return this.diagram;
	}

	public void save() throws ParserConfigurationException {
		Document document = this.xmlFileManager.createDocument();
		Element element = 
			this.diagramXmlManager.getElementFromItem(this.diagram, document);
		
		document.appendChild(element);
		this.xmlFileManager.write(document, this.getComponentFilePath());
		
		this.graphPersistenceService.save(this.getRepresentationFilePath(), this.graph);
	}

	private String getRepresentationFilePath() {
		return this.projectContext.getDataDirectory() + "/" + this.diagram.getName() + "-rep";
	}

	private String getComponentFilePath() {
		return this.projectContext.getDataDirectory() + "/" + this.diagram.getName() + "-comp";
	}


	public void openDiagram(String path) throws Exception {
		Document document = this.xmlFileManager.read(path);
		Element element = document.getDocumentElement();
		this.diagram = this.diagramXmlManager.getItemFromXmlElement(element);
	}
	
	@Override
	public void invoke(Object arg0, mxEventObject arg1) {
		// JGraph has a bug "removed" are those added to the selection. "added" are those that were removed from the selection
		ArrayList added = arg1.getProperties().containsKey("removed") ? (ArrayList)arg1.getProperties().get("removed") : null;
		ArrayList removed = arg1.getProperties().containsKey("added") ? (ArrayList)arg1.getProperties().get("added") : null;
		
		if (removed != null)
		{
			for (Object cell : removed) {
				this.selectedCells.remove((mxCell)cell);
			}
		}
		
		if (added != null)
		{
			for (Object cell : added) {
				if (this.entityCells.containsValue(cell) || this.relationshipCells.containsValue(cell))
				{
					this.selectedCells.add((mxCell)cell);
				}
			}
		}
	}

	public void handleDrop(Point end) {
		if (this.dragStartPoint != null)
		{
			double dx = end.getX() - this.dragStartPoint.getX();
			double dy = end.getY() - this.dragStartPoint.getY();
			
			List<mxCell> attributesCellsToMove = new ArrayList<mxCell>();
			
			for (mxCell cell : this.selectedCells) {
				for (String attributeKey : this.attributeCells.keySet()) {
					if (attributeKey.startsWith(CellConstants.AttributePrefix + cell.getId()))
					{
						mxCell attributeCell = this.attributeCells.get(attributeKey);
						attributesCellsToMove.add(attributeCell);
					}
				}
			}
		
			if (attributesCellsToMove.size() == 0)
			{
				return;
			}
			
			this.graph.getModel().beginUpdate();
			
			try
			{
				this.graph.moveCells(attributesCellsToMove.toArray(), dx, dy);
			}
			finally
			{
				this.graph.getModel().endUpdate();
			}
			
			this.dragStartPoint = null;
		}
	}

	public void handleDragStart(Point start) {
		if (this.dragStartPoint == null && this.selectedCells.size() != 0)
		{
			this.dragStartPoint = new Point(start);
		}
	}

	@Override
	public void createHierarchy() {
		IHierarchyController hierarchyController = this.hierarchyControllerFactory.create();
		hierarchyController.addSuscriber(this);
		hierarchyController.create();
	}

	@Override
	public void handleCreatedEvent(Hierarchy hierarchy) {
		this.graph.getModel().beginUpdate();
		
		try
		{
			Object parent = this.graph.getDefaultParent();
			mxCell hierarchyNode = this.addHierarchyNode(hierarchy, parent);
			
			for (UUID childId : hierarchy.getChildren()) {
				this.connectChildToHierarchy(parent, hierarchyNode, hierarchy.getId(), childId);
			}
		}
		finally {
			this.diagram.getHierarchies().add(hierarchy);
			this.graph.getModel().endUpdate();
		}
	}

	private void connectChildToHierarchy(Object parent, mxCell hierarchyNode, UUID hierarchyId, UUID childId) {
		String stringId = childId.toString();
		mxCell childCell = this.getEntityCell(stringId);
		
		mxCell hierarchyConnectorCell = (mxCell) this.graph
			.insertEdge(parent, null, "", childCell, hierarchyNode, StyleConstants.HIERARCHY_CHILD_CONNECTOR_STYLE);
		
		this.hierarchyConnectorCells.put(CellConstants.HierarchyConnectorPrefix + hierarchyId.toString() + childId, hierarchyConnectorCell);
	}

	private mxCell addHierarchyNode(Hierarchy hierarchy, Object parent) {
		String parentId = hierarchy.getGeneralEntityId().toString();
		mxCell parentCell = this.getEntityCell(parentId);
		double x = parentCell.getGeometry().getCenterX();
		double y = parentCell.getGeometry().getCenterY() + StyleConstants.ENTITY_HEIGHT / 2 + StyleConstants.HIERARCHY_DISTANCE_TO_PARENT;
		mxCell hierarchyNode = (mxCell) this.graph.insertVertex(parent, null, "", x, y, 0, 0);
		mxCell hierarchyConnectorCell = (mxCell) this.graph
			.insertEdge(parent, null, hierarchy.getSummary(), hierarchyNode, parentCell, StyleConstants.HIERARCHY_PARENT_CONNECTOR_STYLE);
		this.hierarchyNodeCells.put(CellConstants.HierarchyNodePrefix + hierarchy.getId().toString(), hierarchyNode);
		this.hierarchyConnectorCells.put(CellConstants.HierarchyConnectorPrefix + hierarchy.getId().toString() + parentId, hierarchyConnectorCell);
		
		return hierarchyNode;
	}
	
	public mxCell getHierarchyNodeCell(String id) {
		return this.hierarchyNodeCells.get(CellConstants.HierarchyNodePrefix + id);
	}

	public mxCell getHierarchyConnectorCell(String id) {
		return this.hierarchyConnectorCells.get(CellConstants.HierarchyConnectorPrefix + id);
	}

	public void addListener(IDiagramEventListener listener) {
		this.listeners.add(listener);
	}
}
