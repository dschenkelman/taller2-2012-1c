package controllers.factories;

import java.util.List;

import controllers.IKeysController;

import models.IKey;

public interface IKeysControllerFactory {
	//IKeysController create();
	IKeysController create(List<IKey> keys);
}
