package controllers.tests.mocks;

import controllers.IKeysController;
import controllers.listeners.IIdGroupEventListener;
import models.IKey;
import views.IKeysView;

public class MockKeyController implements IKeysController {
    private Iterable<IKey> iKeys;
    private boolean createdCall = false;

    @Override
    public void create() {
        this.createdCall = true;
    }

    @Override
    public void addIdGroupToKey() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setKeyView(IKeysView keysView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeIdGroupFromKey() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean validIdGroupName(String name) {
        return false;
    }

    public void setKeys(Iterable<IKey> iKeys) {
        this.iKeys = iKeys;
    }

    public Iterable<IKey> getKeys() {
        return this.iKeys;
    }
    public boolean createdCalled(){
        return this.createdCall;
    }
}
