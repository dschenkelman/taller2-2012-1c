package controllers;

import infrastructure.IProjectContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import controllers.listeners.IRelationshipEntityEventListener;

import models.Cardinality;
import models.RelationshipEntity;
import views.IRelationshipEntityView;

public class RelationshipEntityController extends BaseController implements
		IRelationshipEntityController {

	private List<RelationshipEntity> listRelEnt;
	private IRelationshipEntityView relationshipEntityView;
	private List<IRelationshipEntityEventListener> listeners;

	public RelationshipEntityController(IProjectContext projectContext,
			List<RelationshipEntity> RElist,
			IRelationshipEntityView relationshipEntityView) {
		super(projectContext);
		listeners = new ArrayList<IRelationshipEntityEventListener>();
		this.listRelEnt = RElist;
		this.setRelationshipEntityView(relationshipEntityView);

	}

	@Override
	public void create() {
		this.relationshipEntityView.show();
	}

	@Override
	public void add(UUID uuid, Cardinality card, String role) {
		RelationshipEntity relEntity = new RelationshipEntity(uuid, card, role);
		this.listRelEnt.add(relEntity);
		this.updateSuscribers(relEntity);
	}

	@Override
	public void modify(UUID uuid, Cardinality card, String role)
			throws Exception {
		RelationshipEntity aux = this.findRelationshipEntity(uuid);
		try {
			aux.setCardinality(card);
			aux.setRole(role);
		} catch (NullPointerException e) {
			throw new Exception("Error: Relationship-Entity with UUID " + uuid
					+ " not found");
		}

		this.updateSuscribers(aux);
	}

	@Override
	public void remove(UUID uuid) throws Exception {
		RelationshipEntity aux = this.findRelationshipEntity(uuid);
		if (aux != null)
			listRelEnt.remove(aux);
		else
			throw new Exception("Error: Relationship-Entity with UUID " + uuid
					+ " not found");

		this.updateSuscribers(aux);
	}

	@Override
	public List<RelationshipEntity> getRelationshipEntities() {
		return listRelEnt;
	}

	@Override
	public void setRelationshipEntityView(IRelationshipEntityView view) {
		relationshipEntityView = view;
		relationshipEntityView.setController(this);
	}

	private RelationshipEntity findRelationshipEntity(UUID uuid) {
		Iterator<RelationshipEntity> ite = listRelEnt.iterator();
		while (ite.hasNext()) {
			RelationshipEntity aux = ite.next();
			if (aux.getEntityId() == uuid)
				return aux;
		}
		return null;
	}

	@Override
	public void addSuscriber(IRelationshipEntityEventListener listener) {
		this.listeners.add(listener);
	}

	protected void updateSuscribers(RelationshipEntity relationshipEntity) {
		for (IRelationshipEntityEventListener listener : listeners)
			listener.handleCreatedEvent(relationshipEntity);
	}

}
