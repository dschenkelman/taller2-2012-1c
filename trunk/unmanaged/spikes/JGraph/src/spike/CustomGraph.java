package spike;

import com.mxgraph.view.mxGraph;

public class CustomGraph extends mxGraph 
{
	public CustomGraph()
	{
		this.cellsEditable = true;
		this.cellsResizable = false;
		this.cellsBendable = false;
		this.cellsCloneable = false;
		this.cellsDeletable = false;
		this.cellsDisconnectable = false;
	}
	
	@Override
	public boolean isCellLocked(Object cell) 
	{
		return this.getModel().isEdge(cell);
	}
}
