package jgraph.extensions;

import com.mxgraph.view.mxGraph;

public class CustomGraph extends mxGraph 
{
	public CustomGraph()
	{
		this.cellsEditable = true;
		this.cellsResizable = true;
		this.cellsBendable = true;
		this.cellsCloneable = true;
		this.cellsDeletable = true;
		this.cellsDisconnectable = true;
	}
	
	@Override
	public boolean isCellLocked(Object cell) 
	{
		return false;
	}
}

