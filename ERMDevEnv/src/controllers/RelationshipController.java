package controllers;

import infrastructure.IProjectContext;
import infrastructure.ProjectContext;

import java.util.List;
import java.util.UUID;

import views.IRelationshipView;
import views.mock.MockRelationshipView;
import controllers.factories.IAttributeControllerFactory;
import controllers.factories.IRelationshipEntityControllerFactory;
import controllers.factories.IStrongEntityControllerFactory;
import controllers.factories.mock.MockRelationshipEntityControllerFactory;
import controllers.tests.mocks.MockAttributeControllerFactory;
import controllers.tests.mocks.MockProjectContext;
import controllers.tests.mocks.MockStrongEntityControllerFactory;

import models.Attribute;
import models.Relationship;
import models.RelationshipEntity;

public class RelationshipController implements IRelationshipController {

	private IAttributeController attController;
	private IRelationshipEntityController relEntController;
	private Relationship relationship;
	
	public RelationshipController (ProjectContext projectContext, Relationship rel) {
		
	}
	
	public RelationshipController(
			IProjectContext pContext,
			IRelationshipView view,
			IAttributeControllerFactory attributeControllerFactory,
			IStrongEntityControllerFactory strongEntityControllerFactory,
			IRelationshipEntityControllerFactory relationshipEntityControllerFactory) {
		
		
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

	@Override
	public List<Attribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelationshipEntity> getRelationshipEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
