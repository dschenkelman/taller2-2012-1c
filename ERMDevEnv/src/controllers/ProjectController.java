package controllers;

import infrastructure.IProjectContext;

import java.io.File;

public class ProjectController implements IProjectController {
	private IProjectContext projectContext;
	
	public ProjectController(IProjectContext projectContext) {
		this.projectContext = projectContext;
	}

	public void createProject(String projectName) {
		this.projectContext.setName(projectName);
		new File(this.projectContext.getDataDirectory()).mkdirs();
	}

}
