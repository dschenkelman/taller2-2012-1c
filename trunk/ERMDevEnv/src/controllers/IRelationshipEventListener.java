package controllers;

import models.Relationship;

public interface IRelationshipEventListener {
	public void handleEvent(Relationship relationship);
}
