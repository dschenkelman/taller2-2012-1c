package controllers.tests.mocks;

import controllers.IEventListener;
import models.Entity;

public class MockIEventListener implements IEventListener {
    public boolean called = false;

    @Override
    public void handleEvent(Object... objects) {
        called = true;

    }
}
