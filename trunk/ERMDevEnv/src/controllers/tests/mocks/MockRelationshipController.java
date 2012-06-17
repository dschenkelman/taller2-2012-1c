package controllers.tests.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import views.IRelationshipView;

import models.Attribute;
import models.Cardinality;
import models.RelationshipEntity;
import models.StrongEntityCollection;

import controllers.IRelationshipController;
import controllers.listeners.IRelationshipEventListener;

public class MockRelationshipController implements IRelationshipController{

	private boolean createCalled;
	private List<IRelationshipEventListener> listeners;
	
	public MockRelationshipController(){
		this.createCalled = false;
		this.listeners = new ArrayList<IRelationshipEventListener>();
	}
	
	public boolean createWasCalled() {
		return this.createCalled;
	}

	@Override
	public void create() {
		this.createCalled = true;
	}

	public List<IRelationshipEventListener> getListeners() {
		return this.listeners;
	}

	@Override
	public void addCreateListener(IRelationshipEventListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isComposition() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
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

	@Override
	public void isComposition(boolean composition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRelationshipView(IRelationshipView view) {
		// TODO Auto-generated method stub
		
	}

}
