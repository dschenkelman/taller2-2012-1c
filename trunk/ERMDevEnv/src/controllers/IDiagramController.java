package controllers;

import java.awt.Point;

import javax.xml.parsers.ParserConfigurationException;

import views.IDiagramView;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public interface IDiagramController extends IEntityEventListener, IRelationshipEventListener{
	mxGraph getGraph();
	void createEntity();
	void createRelationship();
	void addEntity(double x, double y) throws Exception;
	mxCell getEntityCell(String id);
	mxCell getAttributeCell(String id);
	mxCell getAttributeConnectorCell(String id);
	boolean hasPendingEntity();
	void save() throws ParserConfigurationException;
	IDiagramView getView();
	void handleDrop(Point end);
	void handleDragStart(Point start);
}
