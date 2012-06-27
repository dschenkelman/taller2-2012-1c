package controllers.tests.mocks;

import models.Entity;
import models.Hierarchy;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import persistence.IGraphPersistenceService;

public class MockGraphPersistenceService implements IGraphPersistenceService {

	private int saveCalls;
	
	private mxGraph graphToSave;

	private String savePath;
	
	private String diagramName;

	@Override
	public void load(String name, mxGraph graph) {
		this.diagramName = name;
		
		mxCell cell = new mxCell();
		cell.setId("Entity1");
		mxCell cell2 = new mxCell();
		cell2.setId("HierarchyNode1");
		
		graph.addCell(cell);
        graph.addCell(cell2);
	}

	public String getDiagramName() {
		return this.diagramName;
	}
	
	@Override
	public void save(String name, mxGraph graph) {
		this.saveCalls++;
		this.graphToSave = graph;
		this.savePath = name;
	}
	
	public int getSaveCalls(){
		return this.saveCalls;
	}

	public mxGraph getGraphToSave(){
		return this.graphToSave;
	}

	public String getSavePath() {
		return this.savePath;
	}
}
