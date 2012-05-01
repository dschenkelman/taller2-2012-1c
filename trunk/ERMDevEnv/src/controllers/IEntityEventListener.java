package controllers;

import models.Entity;

public interface IEntityEventListener {
	public void handleEvent(Entity entity);
}
