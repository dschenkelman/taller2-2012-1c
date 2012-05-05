package controllers.tests.mocks;

import controllers.IKeysController;
import infrastructure.IControllerFactory;
import models.IKey;

import java.util.List;

public class MockKeyControllerFactory implements IControllerFactory<IKeysController, List<IKey>> {

    private boolean createCalled = false;
    private MockKeyController keyController;

    @Override
    public IKeysController create() {
        return null;
    }

    @Override
    public IKeysController create(List<IKey> objects) {
        this.createCalled = true;
        this.keyController.setKeys(objects);
        return this.keyController;
    }

    public boolean createCalled() {
        return this.createCalled;
    }

    public void setKeyController(MockKeyController keyController) {
        this.keyController = keyController;
    }
}
