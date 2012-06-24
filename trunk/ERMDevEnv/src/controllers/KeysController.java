package controllers;

import infrastructure.Func;
import infrastructure.IterableExtensions;
import models.IKey;
import models.IdGroup;
import models.IdGroupCollection;
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
        this.keysView.setExistIdGroup(this.getIdGroupFromKeys());
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
    public boolean validIdGroupName(String name) {
        return !name.equals("") && IterableExtensions.firstOrDefault(this.getIdGroupFromKeys(), new IdGroupCmp(), name) == null;

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

    private Iterable<IdGroup> getIdGroupFromKeys() {
        IdGroupCollection idGroupCollection = new IdGroupCollection();
        for (IKey key : possibleKeys) {
            for (IdGroup idGroup : key.getIdGroup().getIdGroups()) {
                if (!idGroupCollection.exists(idGroup.getName())) {
                    try {
                        idGroupCollection.addIdGroup(idGroup);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return idGroupCollection.getIdGroups();
    }

    private class IdGroupCmp extends Func<IdGroup, String, Boolean> {

        @Override
        public Boolean execute(IdGroup idGroup, String name) {
            return idGroup.getName().equals(name);
        }
    }


}
