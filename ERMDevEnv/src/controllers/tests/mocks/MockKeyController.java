package controllers.tests.mocks;

import controllers.IIdGroupEventListener;
import controllers.IKeysController;
import models.IKey;

public class MockKeyController implements IKeysController {
    private Iterable<IKey> iKeys;

    @Override
    public void addSubscriber(IIdGroupEventListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void create() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addKeys() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setKeys(Iterable<IKey> iKeys) {
        this.iKeys = iKeys;
    }

    public Iterable<IKey> getKeys(){
        return this.iKeys;
    }
}
