package controllers;

import infrastructure.ProjectContext;

import java.util.UUID;

import models.Relationship;

public class RelationshipController implements IRelationshipController {

	private IAttributeController attController;
	private IRelationshipEntityController relEntController;
	private Relationship relationship;
	
	public RelationshipController (ProjectContext projectContext, Relationship rel) {
		
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCreateListener(IRelationshipEventListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isComposition() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRealationshipView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
