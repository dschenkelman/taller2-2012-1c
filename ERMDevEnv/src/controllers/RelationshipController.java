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
import controllers.factories.mock.MockRelationshipEntityControllerFactory;
import controllers.listeners.IRelationshipEventListener;
import controllers.tests.mocks.MockAttributeControllerFactory;
import controllers.tests.mocks.MockProjectContext;


import models.Attribute;
import models.AttributeCollection;
import models.Cardinality;
import models.EntityType;
import models.Relationship;
import models.RelationshipEntity;


public class RelationshipController implements IRelationshipController {

	
	private Relationship pendingRelationship; 
	private IRelationshipView view;
	private List<IRelationshipEventListener> listeners;
	
	//controllers
	private IProjectContext pContext;
	private IRelationshipEntityController relEntController;
	private IAttributeController attController;
		
	
	//factories
	private IAttributeControllerFactory attributeControllerFactory;
	private IRelationshipEntityControllerFactory relationshipEntityControllerFactory;

	
	public RelationshipController(
			IProjectContext pContext,
			Relationship relationship, IRelationshipView view,
			IAttributeControllerFactory attributeControllerFactory,
			IRelationshipEntityControllerFactory relationshipEntityControllerFactory) {
		
		pendingRelationship = relationship;
		this.pContext = pContext;
		this.view = view;
		this.attributeControllerFactory = attributeControllerFactory;
		this.relationshipEntityControllerFactory = relationshipEntityControllerFactory;	
		listeners = new ArrayList<IRelationshipEventListener> ();
		
	}

	@Override
	public void create() {
		
		
		view.setController(this);
		relEntController = relationshipEntityControllerFactory
				.create(IterableExtensions.getListOf(pendingRelationship.getRelationshipEntities()));
		attController = this.attributeControllerFactory.create();
		attController.setAttributes(this.pendingRelationship.getAttributes().getAttributes());

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
	public void add() throws Exception {
		if (this.pendingRelationship.getName()==null || this.pendingRelationship.getName() =="")
			throw new Exception ("The name field  is not completed");
		
		
		if (this.relEntController.getRelationshipEntities().size()<2)
			throw new Exception ();
		this.pendingRelationship.setRelationshipEntities
		(this.relEntController.getRelationshipEntities());
		
		checkComposition();
				
		addAttributes();
		//TODO:Checkear si tengo que setear en invisible la vista
	       	        
	    for (IRelationshipEventListener listener : listeners) 
	       	listener.handleCreatedEvent(pendingRelationship);
	      	    	
	}


	private void checkComposition() throws Exception {
		if (isComposition() == false) return;
		
		EntityType entityType = null;
		for (RelationshipEntity ent : pendingRelationship.getRelationshipEntities() ) {
			if (entityType == null)
				entityType = this.pContext.getEntity(ent.getEntityId()).getType();
			else if (entityType != this.pContext.getEntity(ent.getEntityId()).getType()) {
				isComposition(false);
				throw new Exception("All the entities should be the same type");
			}
		}
	}

	private void addAttributes () {
		AttributeCollection attributeCollection = this.pendingRelationship.getAttributes();
        for (Attribute attribute : this.attController.getAttributes()) {
            try {
                attributeCollection.addAttribute(attribute);
            } catch (Exception e) {
                //When editing an Relationship
                e.printStackTrace();
            }
        }
	}
	
}
