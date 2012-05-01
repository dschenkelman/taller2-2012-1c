package controllers;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public interface IDiagramController extends IEntityEventListener, IRelationshipEventListener{
	mxGraph getGraph();
	void createEntity();
	void addEntity(double x, double y);
	mxCell getEntityCell(String id);
	mxCell getAttributeCell(String id);
	mxCell getAttributeConnectorCell(String id);
	boolean hasPendingEntity();
}
