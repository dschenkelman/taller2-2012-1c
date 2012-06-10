package controllers.tests.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Entity;
import views.IHierarchyView;
import controllers.IHierarchyController;
import controllers.IHierarchyEventListener;

public class MockHierarchyController implements IHierarchyController {

	private int createCalls;
	private List<IHierarchyEventListener> listeners;
	
	public MockHierarchyController(){
		this.listeners = new ArrayList<IHierarchyEventListener>();
	}

	@Override
	public boolean addHierarchy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addSpecificEntity(Entity entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addSuscriber(IHierarchyEventListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void create() {
		this.createCalls++;		
	}

	@Override
	public UUID getGeneralEntityUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeSpecificEntity(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setExclusive(boolean exclusive) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setGeneralEntity(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHierachyView(IHierarchyView hierarchyView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTotal(boolean total) {
		// TODO Auto-generated method stub
		
	}

	public int getCreateCallsCount() {
		return this.createCalls;
	}

	public List<IHierarchyEventListener> getListeners() {
		return this.listeners;
	}

	@Override
	public Iterable<Entity> getAvailableEntities() {
		// TODO Auto-generated method stub
		return null;
	}

}
