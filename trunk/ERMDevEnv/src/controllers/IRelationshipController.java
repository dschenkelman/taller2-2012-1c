package controllers;

import java.util.List;

import models.Attribute;
import models.RelationshipEntity;

public interface IRelationshipController {
	
	void create();
	void addCreateListener(IRelationshipEventListener listener);
	
	void setName(String name);
	String getName();
	
	boolean isComposition () ;
	void add();
	
	void setRealationshipView ();
	List<Attribute> getAttributes();
	List<RelationshipEntity> getRelationshipEntities();
	
	
}
