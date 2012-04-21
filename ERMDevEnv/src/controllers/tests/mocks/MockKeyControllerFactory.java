package controllers.tests.mocks;

import controllers.IKeysController;
import infrastructure.IControllerFactory;
import models.IKey;

public class MockKeyControllerFactory implements IControllerFactory<IKeysController, Iterable<IKey>> {

    private boolean createCalled = false;
    private MockKeyController  keyController;

    @Override
    public IKeysController create() {
        return null;
    }

    @Override
    public IKeysController create(Iterable<IKey> iKeys) {
        this.createCalled = true;
        this.keyController.setKeys(iKeys);
        return this.keyController;
    }

    public boolean createCalled(){
        return this.createCalled;
    }

    public void setKeyController(MockKeyController keyController){
        this.keyController = keyController;
    }
}
