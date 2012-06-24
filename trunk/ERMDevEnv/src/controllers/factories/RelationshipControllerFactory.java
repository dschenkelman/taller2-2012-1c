package controllers.factories;

import java.util.Random;
import views.IRelationshipView;

import models.Cardinality;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import controllers.IRelationshipController;
import controllers.factories.mock.MockEntityControllerFactory;
import controllers.listeners.IRelationshipEventListener;

public class RelationshipControllerFactory implements
		IRelationshipControllerFactory {

	@Override
	public IRelationshipController create() {
		return new IRelationshipController() {
			
			private IRelationshipEventListener listener;
			
			@Override
			public void create() {
				Random r = new Random();
				int entityNumber = r.nextInt(MockEntityControllerFactory.Entities.size());
				Entity entity1 = MockEntityControllerFactory.Entities.get(entityNumber);
				
				entityNumber = r.nextInt(MockEntityControllerFactory.Entities.size());
				
				Entity entity2 = MockEntityControllerFactory.Entities.get(entityNumber);
				
				try {
					Relationship relationship = new Relationship(
							new RelationshipEntity(entity1, new Cardinality(0, 1), entity1.getName() + "A"), 
							new RelationshipEntity(entity2, new Cardinality(0, 1), entity2.getName() + "B"));
					
					relationship.getAttributes().addAttribute("Attribute1");
					relationship.getAttributes().addAttribute("Attribute2");
					this.listener.handleCreatedEvent(relationship);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void addCreateListener(IRelationshipEventListener listener) {
					this.listener = listener;
				
			}

			@Override
			public boolean isComposition() {
				// TODO Auto-generated method stub
				return false;
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
			public void isComposition(boolean composition) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setRelationshipView(IRelationshipView view) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void add() {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public IRelationshipController create(Relationship relationship) {
		return null;
	}

}
