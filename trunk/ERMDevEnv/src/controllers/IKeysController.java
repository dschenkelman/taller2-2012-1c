package controllers;

import controllers.listeners.IIdGroupEventListener;
import views.IKeysView;

public interface IKeysController {
    public void addSubscriber(IIdGroupEventListener listener);

    public void create();

    public void addKeys();

    void setKeyView(IKeysView keysView);
}
