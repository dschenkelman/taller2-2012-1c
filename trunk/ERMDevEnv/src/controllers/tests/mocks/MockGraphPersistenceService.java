package controllers.tests.mocks;

import com.mxgraph.view.mxGraph;

import persistence.IGraphPersistenceService;

public class MockGraphPersistenceService implements IGraphPersistenceService {

	private int saveCalls;
	
	private mxGraph graphToSave;

	private String savePath;

	@Override
	public void load(String name, mxGraph graph) {
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
