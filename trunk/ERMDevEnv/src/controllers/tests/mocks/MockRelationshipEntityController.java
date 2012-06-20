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
	
	
	public MockRelationshipEntityController(IProjectContext projectContext, List<RelationshipEntity> relEnt) {
		super(projectContext);
		relationshipEntity = relEnt;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(UUID uuid, Cardinality card, String role) {
		relationshipEntity.add(new RelationshipEntity (uuid,card,role));
	}

	@Override
	public void modify(UUID uuid, Cardinality card, String role)
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

	@Override
	public void setRelationshipEntityView(IRelationshipEntityView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSuscriber(IRelationshipEntityEventListener listener) {
		// TODO Auto-generated method stub

	}

}
