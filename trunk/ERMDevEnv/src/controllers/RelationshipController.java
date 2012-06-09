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
import models.AttributeCollection;
import models.Cardinality;
import models.Relationship;
import models.RelationshipEntity;
import models.StrongEntityCollection;

public class RelationshipController implements IRelationshipController {

	
	private Relationship pendingRelationship; 
	private IRelationshipView view;
	
	//controllers
	private IProjectContext pContext;
	private IRelationshipEntityController relEntController;
	private IAttributeController attController;
	private IStrongEntityController strongEntController;
	
	
	//factories
	private IAttributeControllerFactory attributeControllerFactory;
	private IStrongEntityControllerFactory strongEntityControllerFactory;
	private IRelationshipEntityControllerFactory relationshipEntityControllerFactory;

	
	public RelationshipController(
			IProjectContext pContext,
			Relationship relationship, IRelationshipView view,
			IAttributeControllerFactory attributeControllerFactory,
			IStrongEntityControllerFactory strongEntityControllerFactory,
			IRelationshipEntityControllerFactory relationshipEntityControllerFactory) {
		
		pendingRelationship = relationship;
		this.pContext = pContext;
		this.view = view;
		this.attributeControllerFactory = attributeControllerFactory;
		this.strongEntityControllerFactory = strongEntityControllerFactory;
		this.relationshipEntityControllerFactory = relationshipEntityControllerFactory;	
	}

	@Override
	public void create() {
		view.setController(this);
		relEntController = relationshipEntityControllerFactory.create(pendingRelationship);
		attController = this.attributeControllerFactory.create(new AttributeCollection());
		strongEntController = strongEntityControllerFactory.create(new StrongEntityCollection());
	}

	@Override
	public void addCreateListener(IRelationshipEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		pendingRelationship.setName(name);
	}

	@Override
	public String getName() {
		return pendingRelationship.getName();
	}

	@Override
	public void isComposition(boolean composition) {
		 pendingRelationship.isComposition(composition);
	}
	
	@Override
	public boolean isComposition ()  {
		return pendingRelationship.isComposition();
	}

	@Override
	public void setRelationshipView(IRelationshipView view) {
		this.view = view;		
	}

	@Override
	public Iterable<Attribute> getAttributes() {
		return attController.getAttributes();
	}

	@Override
	public List<RelationshipEntity> getRelationshipEntities() {
		return relEntController.getRelationshipEntities();
	}

	@Override
	public void addRelationshipEntity(RelationshipEntity relationshipEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRelationshipEntity(UUID randomUUID, Cardinality cardinality,
			String role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRelationship() {
		
		
	}

	@Override
	public StrongEntityCollection getStrongEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	
}
