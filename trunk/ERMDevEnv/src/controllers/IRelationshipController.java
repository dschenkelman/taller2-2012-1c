package controllers;

import java.util.List;
import java.util.UUID;

import controllers.listeners.IRelationshipEventListener;

import views.IRelationshipView;

import models.Attribute;
import models.Cardinality;
import models.RelationshipEntity;
import models.StrongEntityCollection;

public interface IRelationshipController {
	
	void create();
	void addCreateListener(IRelationshipEventListener listener);
	
	void setName(String name);
	String getName();
	
	
	void isComposition(boolean composition);
	boolean isComposition();
	
	
	Iterable<Attribute>  getAttributes();
	List<RelationshipEntity> getRelationshipEntities();
	void addRelationshipEntity(RelationshipEntity relationshipEntity);
	void addRelationshipEntity(UUID randomUUID, Cardinality cardinality,
			String role);
	
	
	void addRelationship();
	StrongEntityCollection getStrongEntities();
	int getType();
	void setRelationshipView(IRelationshipView view);
	
	
	
	

	
	
	
}
