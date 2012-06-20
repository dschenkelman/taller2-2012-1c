package controllers;

import java.util.List;
import java.util.UUID;

import controllers.listeners.IRelationshipEventListener;

import views.IRelationshipView;

import models.Attribute;
import models.Cardinality;
import models.Relationship;
import models.RelationshipEntity;
import models.StrongEntityCollection;

public interface IRelationshipController {
	
	void create();
	void addCreateListener(IRelationshipEventListener listener);
	
	void setName(String name);
	String getName();
	
	
	void isComposition(boolean composition);
	boolean isComposition();
	
	void add();
	void setRelationshipView(IRelationshipView view);
	
	
	
	

	
	
	
}
