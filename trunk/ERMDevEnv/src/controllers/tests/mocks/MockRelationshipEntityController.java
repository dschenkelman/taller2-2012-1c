package controllers.tests.mocks;

import infrastructure.IProjectContext;

import java.util.List;
import java.util.UUID;

import models.Cardinality;
import models.RelationshipEntity;
import views.IRelationshipEntityView;
import controllers.BaseController;
import controllers.IRelationshipEntityController;
import controllers.listeners.IRelationshipEntityEventListener;

public class MockRelationshipEntityController extends BaseController implements
		IRelationshipEntityController {
	
	private List<RelationshipEntity> relationshipEntity;
	
	
	public MockRelationshipEntityController(IProjectContext projectContext) {
		super(projectContext);
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override

	public void add(UUID uuid, Cardinality card, String role,boolean isStrong) {
		relationshipEntity.add(new RelationshipEntity (uuid,card,role,isStrong));
	}

	@Override
	public void modify(UUID uuid, Cardinality card, String role,boolean isStrong)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(UUID uuid) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RelationshipEntity> getRelationshipEntities() {
		return relationshipEntity;
	}
	
	public void setRelationshipEntities(List<RelationshipEntity> list) {
		relationshipEntity = list;
	}


	@Override
	public void setRelationshipEntityView(IRelationshipEntityView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSuscriber(IRelationshipEntityEventListener listener) {
		// TODO Auto-generated method stub

	}

}
