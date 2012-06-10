package controllers;

import java.util.UUID;

import models.Entity;
import models.EntityCollection;
import views.IHierarchyView;

public interface IHierarchyController {
	
	void create();

	void setHierachyView(IHierarchyView hierarchyView);

	void addSuscriber(IHierarchyEventListener listener);

	boolean addHierarchy();

	boolean setGeneralEntity(Entity entity);

	UUID getGeneralEntityUUID();

	boolean addSpecificEntity(Entity entity) throws Exception;

	boolean removeSpecificEntity(Entity entity);

	void setTotal(boolean total);

	void setExclusive(boolean exclusive);

	Iterable<Entity> getAvailableEntities();
}
