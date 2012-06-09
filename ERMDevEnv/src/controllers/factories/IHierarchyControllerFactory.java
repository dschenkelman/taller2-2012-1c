package controllers.factories;

import models.Hierarchy;
import controllers.IHierarchyController;

public interface IHierarchyControllerFactory {
	IHierarchyController create();
	IHierarchyController create(Hierarchy hierarchy);
}
