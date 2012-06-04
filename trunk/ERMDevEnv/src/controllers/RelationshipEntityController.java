package controllers;

import infrastructure.IProjectContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Cardinality;
import models.RelationshipEntity;
import views.IRelationshipEntityView;
import views.RelatinshipEntityView;

public class RelationshipEntityController implements  IRelationshipEntityController {

	public RelationshipEntityController(IProjectContext projectContext,
			ArrayList<RelationshipEntity> arrayList,
			IRelationshipEntityView mockRelationshipEntityView) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void add(UUID uuid, Cardinality card, String role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(UUID uuid, Cardinality card, String role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(UUID uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RelationshipEntity> getRelationshipEntities() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addCreateListener(IRelationshipEntityEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRelationshipEntityView(IRelationshipEntityView view) {
		// TODO Auto-generated method stub
		
	}



}
