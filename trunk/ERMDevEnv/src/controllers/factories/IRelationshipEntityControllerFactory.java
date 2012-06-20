package controllers.factories;

import java.util.List;

import models.Relationship;
import models.RelationshipEntity;
import controllers.IRelationshipEntityController;

public interface IRelationshipEntityControllerFactory {

	IRelationshipEntityController create(List<RelationshipEntity> relationshipEntities);

}
