package controllers.factories;

import models.Relationship;
import controllers.IRelationshipEntityController;

public interface IRelationshipEntityControllerFactory {

	IRelationshipEntityController create(Relationship pendingRelationship);

}
