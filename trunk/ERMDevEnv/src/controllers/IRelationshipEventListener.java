package controllers;

import models.Relationship;

public interface IRelationshipEventListener {
	public void handleCreatedEvent(Relationship relationship);
}
