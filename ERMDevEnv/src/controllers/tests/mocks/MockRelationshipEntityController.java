package controllers.tests.mocks;

import infrastructure.IProjectContext;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Cardinality;
import models.Entity;
import models.EntityCollection;
import models.RelationshipEntity;
import models.tests.EntityCollectionTestCase;
import views.IRelationshipEntityView;
import controllers.BaseController;
import controllers.IRelationshipEntityController;
import controllers.listeners.IRelationshipEntityEventListener;

public class MockRelationshipEntityController extends BaseController implements
		IRelationshipEntityController {
	
	private List<RelationshipEntity> relationshipEntity;
	private EntityCollection entCol;	
	
	public MockRelationshipEntityController(IProjectContext projectContext) {
		super(projectContext);
		entCol = new EntityCollection();
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

	public void createTestingList () {
		Entity ent1 = new Entity ("Entity1");
		Entity ent2 = new Entity ("Entity2");
		Entity ent3 = new Entity ("Entity3");
		Entity ent4 = new Entity ("Entity4");
		Entity ent5 = new Entity ("Entity5");
		Entity ent6 = new Entity ("Entity6");
		entCol = new EntityCollection();
		entCol.add(ent1); 
		entCol.add(ent2);
		entCol.add(ent3);
		entCol.add(ent4);
		entCol.add(ent5);
		entCol.add(ent6);
		System.out.println("Creating test List "+ entCol.count());
		relationshipEntity = new ArrayList<RelationshipEntity>();
		
		try {
			relationshipEntity.add(new RelationshipEntity(ent1,new Cardinality(0, 5),"role1"));
			relationshipEntity.add(new RelationshipEntity(ent2));
			relationshipEntity.add(new RelationshipEntity(ent3));
			RelationshipEntity rel1 = new RelationshipEntity(ent4);
			rel1.setStrongEntity(true);
			relationshipEntity.add(rel1);
			relationshipEntity.add(new RelationshipEntity(ent5));
			relationshipEntity.add(new RelationshipEntity(ent6));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private Entity findEntity(UUID id) {
		
		for (Entity ent : entCol) {
			if (ent.getId().equals(id)) {
				System.out.println("Encontrado " + id);
				return ent;
			}
			
		}
		return null;
	}
	
	
	@Override
	public List<Object[]> getListForModel() {
		List<Object[]> list = new ArrayList<Object[]>();
		for (RelationshipEntity relEnt : relationshipEntity) {
				Entity ent  = findEntity(relEnt.getEntityId()); 			
								
				String minCard =  (relEnt.getCardinality()!=null) ?Double.toString(relEnt.getCardinality().getMinimum()):"0"; 
				String maxCard =  (relEnt.getCardinality()!=null) ?Double.toString(relEnt.getCardinality().getMaximum()):"0";
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
			
		return list;
	}

	@Override
	public Iterable<Entity> getEntities() {
		return entCol ;
	}

	@Override
	public boolean entitiesAreSameType() {
		// TODO Auto-generated method stub
		return false;
	}

}
