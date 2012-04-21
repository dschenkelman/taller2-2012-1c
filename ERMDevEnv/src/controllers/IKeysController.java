package controllers;

import java.util.HashMap;

public interface IKeysController {
    public void addSubscriber(IEventListener listener);

    public void create();

    public void addKeys();
}
