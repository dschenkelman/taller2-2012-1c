package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;

public class IdGroupCollection {


    public IdGroupCollection() {
        this.idGroups = new ArrayList<IdGroup>();
    }

    public Iterable<IdGroup> getIdGroups() {
        return this.idGroups;
    }

    public IdGroup addIdGroup(IdGroup idGroup) throws Exception {
        if (!this.exists(idGroup.getNumber())) {
            this.idGroups.add(idGroup);
            return idGroup;
        } else {
            throw new Exception("Group's number: " + idGroup.getNumber() + "already exists for this id");
        }
    }

    public void removeIdGroup(IdGroup idGroup) throws Exception {

        if (this.exists(idGroup.getNumber())) {
            this.idGroups.remove(idGroup);
        } else {
            throw new Exception("Group's number: " + idGroup.getNumber() + "do not exists");
        }

    }

    public IdGroup getIdGroup(Integer number){
        return IterableExtensions.firstOrDefault(this.idGroups, new IntegerCmpFunc(), number);
    }

    public boolean exists(Integer idGroup) {
        return IterableExtensions.firstOrDefault(this.idGroups, new IntegerCmpFunc(), idGroup) != null;
    }

    public int count() {
        return this.idGroups.size();
    }


    private ArrayList<IdGroup> idGroups;

    private class IntegerCmpFunc extends Func<IdGroup, Integer, Boolean> {

        @Override
        public Boolean execute(IdGroup idGroup, Integer idGroupNumber) {
            return idGroup.getNumber().equals(idGroupNumber);
        }
    }


}
