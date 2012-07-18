package controllers.factories;

import java.util.List;

import models.RelationshipEntity;
import controllers.IRelationshipEntityController;

public interface IRelationshipEntityControllerFactory {

	IRelationshipEntityController create();

}
