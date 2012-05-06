package controllers.factories;

import controllers.IRelationshipController;
import models.Relationship;

public interface IRelationshipControllerFactory {
	IRelationshipController create();
	IRelationshipController create(Relationship relationship);
}
