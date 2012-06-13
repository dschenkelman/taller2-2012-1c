package controllers;

import javax.swing.tree.TreeModel;

import views.IProjectView;

public interface IProjectController {
	void createProject(String string);
	IDiagramController getCurrentDiagramController();
	IProjectView getView();
	TreeModel getProjectTree();
}
