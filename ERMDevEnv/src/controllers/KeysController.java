package controllers;

import models.IKey;
import views.IKeysView;

import infrastructure.IProjectContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeysController extends BaseController implements IKeysController {

    private IKeysView keysView;
    private Iterable<IKey> possibleKeys;
    private List<IIdGroupEventListener> listeners;

    public KeysController(IProjectContext projectContext, IKeysView keysView, Iterable<IKey> possibleKeys) {
        super(projectContext);
        this.possibleKeys = possibleKeys;
        this.listeners = new ArrayList<IIdGroupEventListener>();
        setKeyView(keysView);
    }

    @Override
    public void setKeyView(IKeysView keysView) {
        this.keysView = keysView;
        this.keysView.setPossibleKeys(this.possibleKeys);
        this.keysView.setController(this);
    }

    @Override
    public void addSubscriber(IIdGroupEventListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void create() {
        this.keysView.show();
    }

    @Override
    public void addKeys() {
        HashMap<Integer, List<IKey>> keys = this.keysView.getKeys();
        for (IIdGroupEventListener listener : this.listeners) {
            listener.handleEvent(keys);
        }
    }


}
