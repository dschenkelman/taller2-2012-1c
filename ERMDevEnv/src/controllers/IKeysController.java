package controllers;

import controllers.listeners.IIdGroupEventListener;
import views.IKeysView;

public interface IKeysController {

    public void create();

    public void addIdGroupToKey();

    public void setKeyView(IKeysView keysView);

    public void removeIdGroupFromKey();

    void setToKey(boolean bool);
}
