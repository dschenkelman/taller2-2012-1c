package controllers;

import java.util.List;
import java.util.UUID;

import controllers.listeners.IRelationshipEventListener;

import views.IRelationshipView;

import models.Attribute;
import models.Cardinality;
import models.Relationship;
import models.RelationshipEntity;


public interface IRelationshipController {
	
	void create();
	void addCreateListener(IRelationshipEventListener listener);
	
	void setName(String name);
	String getName();
	
	
	void isComposition(boolean composition) throws Exception;
	boolean isComposition();
	
	void add() throws Exception;

	void setRelationshipView(IRelationshipView view);
	void create(Relationship relationship);
	
	
	
	

	
	
	
}
