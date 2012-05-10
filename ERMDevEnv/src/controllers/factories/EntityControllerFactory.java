package controllers.factories;

import views.IEntityView;
import models.Entity;
import controllers.IEntityController;
import controllers.IEntityEventListener;

public class EntityControllerFactory implements IEntityControllerFactory {

	private static int InstanceNumber = 0;
	
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
				Entity entity = new Entity("Entity" + (++InstanceNumber));
				try {
					entity.getAttributes().addAttribute("Attribute1");
					entity.getAttributes().addAttribute("Attribute2");
					entity.getAttributes().addAttribute("Attribute3");
					entity.getAttributes().addAttribute("Attribute4");
					entity.getAttributes().addAttribute("Attribute5");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listener.handleCreatedEvent(entity);
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
