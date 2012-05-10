package jgraph.extensions;

import com.mxgraph.view.mxGraph;

public class CustomGraph extends mxGraph 
{
	public CustomGraph()
	{
		this.cellsEditable = false;
		this.cellsResizable = false;
		this.cellsBendable = false;
		this.cellsCloneable = false;
		this.cellsDeletable = true;
		this.cellsDisconnectable = false;
	}
	
	@Override
	public boolean isCellLocked(Object cell) 
	{
		return this.getModel().isEdge(cell);
	}
}

