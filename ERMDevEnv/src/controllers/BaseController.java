package controllers;

public abstract class BaseController {
	
	protected IProjectContext projectContext;
	
	protected BaseController(IProjectContext projectContext)
	{
		this.projectContext = projectContext;
	}
}
