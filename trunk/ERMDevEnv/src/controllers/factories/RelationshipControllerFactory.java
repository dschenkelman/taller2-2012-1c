package controllers.factories;

import models.Relationship;
import controllers.IRelationshipController;

public class RelationshipControllerFactory implements
		IRelationshipControllerFactory {

	@Override
	public IRelationshipController create() {
		return null;
	}

	@Override
	public IRelationshipController create(Relationship relationship) {
		return null;
	}

}
