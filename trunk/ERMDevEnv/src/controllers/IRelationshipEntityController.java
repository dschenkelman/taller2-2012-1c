package controllers;

import java.util.List;
import java.util.UUID;

import controllers.listeners.IRelationshipEntityEventListener;

import models.Cardinality;
import models.RelationshipEntity;

import views.IRelationshipEntityView;
import views.RelatinshipEntityView;

public interface IRelationshipEntityController {

	public void create();
	public void add(UUID uuid,Cardinality card, String role);
	public void modify(UUID uuid,Cardinality card, String role) throws Exception;
	public void remove(UUID uuid) throws Exception;
	public List<RelationshipEntity> getRelationshipEntities();
	public void setRelationshipEntityView (IRelationshipEntityView view);
	void addSuscriber(IRelationshipEntityEventListener listener);
	
}
