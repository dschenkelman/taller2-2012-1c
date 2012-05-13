package controllers.factories;

import java.util.Random;

import controllers.factories.mock.MockEntityControllerFactory;
import models.Cardinality;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;
import controllers.IRelationshipController;
import controllers.IRelationshipEventListener;

public class RelationshipControllerFactory implements
		IRelationshipControllerFactory {

	@Override
	public IRelationshipController create() {
		return new IRelationshipController() {
			
			private IRelationshipEventListener listener;
			
			@Override
			public void create() {
				Random r = new Random();
				int entityNumber = r.nextInt(MockEntityControllerFactory.Entites.size());
				Entity entity1 = MockEntityControllerFactory.Entites.get(entityNumber);
				
				entityNumber = r.nextInt(MockEntityControllerFactory.Entites.size());
				
				Entity entity2 = MockEntityControllerFactory.Entites.get(entityNumber);
				
				try {
					this.listener.handleCreatedEvent(new Relationship(
							new RelationshipEntity(entity1, new Cardinality(0, 1), entity1.getName() + "A"), 
							new RelationshipEntity(entity2, new Cardinality(0, 1), entity2.getName() + "B")));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void addCreateListener(IRelationshipEventListener listener) {
					this.listener = listener;
				
			}
		};
	}

	@Override
	public IRelationshipController create(Relationship relationship) {
		return null;
	}

}
