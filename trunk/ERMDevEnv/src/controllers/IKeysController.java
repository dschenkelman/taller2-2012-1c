package controllers;

import java.util.HashMap;

public interface IKeysController {
    public void addSubscriber(IEventListener<HashMap<Integer, String>> listener);

    public void create();

    public void addKeys(HashMap<Integer,String> keys);
}
