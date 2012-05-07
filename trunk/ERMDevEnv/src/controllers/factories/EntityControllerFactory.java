package controllers.factories;

import views.IEntityView;
import models.Entity;
import controllers.IEntityController;
import controllers.IEntityEventListener;

public class EntityControllerFactory implements IEntityControllerFactory {

	@Override
	public IEntityController create() {
		return new IEntityController() {
			
			private IEntityEventListener listener;

			@Override
			public boolean validateEntityName(String name) {
				return false;
			}
			
			@Override
			public void setEntityView(IEntityView entityView) {
			}
			
			@Override
			public void create() {
				listener.handleCreatedEvent(new Entity("Entity"));
			}
			
			@Override
			public void addSubscriber(IEntityEventListener listener) {
				this.listener = listener;
			}
			
			@Override
			public boolean addEntity() {
				return true;
			}
		};
	}

	@Override
	public IEntityController create(Entity relationship) {
		return null;
	}

}
