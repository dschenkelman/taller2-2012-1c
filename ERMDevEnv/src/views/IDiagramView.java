package views;

import controllers.IDiagramController;

public interface IDiagramView {
	void setController(IDiagramController controller);
	void refreshGraphComponent();
	boolean showDeleteDialog(String name, boolean couldDelete);
}
