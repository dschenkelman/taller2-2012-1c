package controllers.tests.mocks;

import controllers.IEventListener;
import models.Entity;

public class MockIEventListener implements IEventListener {
    public boolean called = false;
    private Object[] entity;

    @Override
    public void handleEvent(Object... objects) {
        called = true;
        this.entity = objects;

    }
    
    public Object[] get(){
        return this.entity;
    }
}
