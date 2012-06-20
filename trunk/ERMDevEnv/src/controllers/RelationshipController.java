package controllers;

import infrastructure.IProjectContext;
import infrastructure.IterableExtensions;
import infrastructure.ProjectContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import views.IRelationshipView;
import views.mock.MockRelationshipView;
import controllers.factories.IAttributeControllerFactory;
import controllers.factories.IRelationshipEntityControllerFactory;
import controllers.factories.IStrongEntityControllerFactory;
import controllers.factories.mock.MockRelationshipEntityControllerFactory;
import controllers.listeners.IRelationshipEventListener;
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
	private List<IRelationshipEventListener> listeners;
	
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
		listeners = new ArrayList<IRelationshipEventListener> ();
		
	}

	@Override
	public void create() {
		
		
		view.setController(this);
		relEntController = relationshipEntityControllerFactory
				.create(IterableExtensions.getListOf(pendingRelationship.getRelationshipEntities()));
		attController = this.attributeControllerFactory.create(pendingRelationship.getAttributes());
		strongEntController = strongEntityControllerFactory.create(new StrongEntityCollection());
		this.view.show();
	}

	@Override
	public void addCreateListener(IRelationshipEventListener listener) {
		listeners.add(listener);
		
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
	public void add() {
		 
		AttributeCollection attributeCollection = this.pendingRelationship.getAttributes();
	        for (Attribute attribute : this.attController.getAttributes()) {
	            try {
	                attributeCollection.addAttribute(attribute);
	            } catch (Exception e) {
	                //When editing an Relationship
	                e.printStackTrace();
	            }
	        }
	        
	        //TODO:Checkear si tengo que setear en invisible la vista
	        
	        
	        for (IRelationshipEventListener listener : listeners) 
	        	listener.handleCreatedEvent(pendingRelationship);
	      	    	
	}

	
}
