package controllers;

import infrastructure.IProjectContext;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import controllers.listeners.IRelationshipEntityEventListener;

import models.Cardinality;
import models.Entity;
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
	public void add(UUID uuid, Cardinality card, String role, boolean isStrong) {
		RelationshipEntity relEntity = new RelationshipEntity(uuid, card, role,
				isStrong);
		this.listRelEnt.add(relEntity);
		this.updateSuscribers(relEntity);
	}

	@Override
	public void modify(UUID uuid, Cardinality card, String role,
			boolean isStrong) throws Exception {
		RelationshipEntity aux = this.findRelationshipEntity(uuid);
		try {
			aux.setCardinality(card);
			aux.setRole(role);
			aux.setStrongEntity(isStrong);
		} catch (NullPointerException e) {
			throw new Exception("Error: Relationship-Entity with UUID " + uuid
					+ " not found");
		}

		this.updateSuscribers(aux);
	}

	@Override
	public List<Object[]> getListForModel() {

		List<Object[]> list = new ArrayList<Object[]>();
		for (RelationshipEntity relEnt : listRelEnt) {
			Entity ent = findEntity(relEnt.getEntityId());
			
			if (ent != null) {
				String minCard =  (relEnt.getCardinality()!=null) ?Double.toString(relEnt.getCardinality().getMinimum()):""; 
				String maxCard =  (relEnt.getCardinality()!=null) ?Double.toString(relEnt.getCardinality().getMaximum()):"";
				String role = (relEnt.getRole()!= null) ? relEnt.getRole():"";
							
				Object[] obj = new Object[] {
					ent,
					minCard,
					maxCard,
					role, 
					relEnt.isStrongEntity() 
				};

				list.add(obj);
			}
		}
		
		return list;
	}
			
	
	

	private Entity findEntity(UUID id) {
		for (Entity ent : projectContext.getAllEntities()) {
			if (ent.getId().equals(id)) return ent;
		}
		return null;
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

	@Override
	public Iterable<Entity> getEntities() {
		return projectContext.getAllEntities();
	}

	@Override
	public boolean entitiesAreSameType() {
		List<Object []> list = relationshipEntityView.getModelList();
		for (int i = 1 ; i < list.size(); i++ ){
			Entity ent1 = (Entity) list.get(i-1)[0];
			Entity ent2 = (Entity ) list.get(i)[0];
			if (ent1.getType() != ent2.getType()) 
				return false;
		}
		return true;
	}

}
