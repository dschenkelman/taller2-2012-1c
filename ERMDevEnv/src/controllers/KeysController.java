package controllers;

import models.IKey;
import models.IdGroup;
import views.IKeysView;

import infrastructure.IProjectContext;

import java.util.ArrayList;
import java.util.List;

import controllers.listeners.IIdGroupEventListener;

public class KeysController extends BaseController implements IKeysController {

    private IKeysView keysView;
    private Iterable<IKey> possibleKeys;

    public KeysController(IProjectContext projectContext, IKeysView keysView, Iterable<IKey> possibleKeys) {
        super(projectContext);
        this.possibleKeys = possibleKeys;
        setKeyView(keysView);
    }

    @Override
    public void setKeyView(IKeysView keysView) {
        this.keysView = keysView;
        this.keysView.setPossibleKeys(this.possibleKeys);
        this.keysView.setController(this);
    }

    @Override
    public void create() {
        this.keysView.showView();
    }

    @Override
    public void removeIdGroupFromKey() {
        IdGroup idGroup = this.keysView.getIdGroupSelected();
        IKey key = this.keysView.getKeySelectedToRemove();
        try {
            key.getIdGroup().removeIdGroup(idGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addIdGroupToKey() {
        IdGroup idGroup = this.keysView.getIdGroupSelected();
        IKey key = this.keysView.getKeySelectedToAdd();
        try {
            key.getIdGroup().addIdGroup(idGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
