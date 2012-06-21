package controllers;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import views.IProjectView;

public interface IProjectController {
	void createProject(String string);
	void openProject(String string) throws Exception;
	IDiagramController getCurrentDiagramController();
	IProjectView getView();
	TreeModel getProjectTree();
	void changeElement(TreePath treePath);
}
