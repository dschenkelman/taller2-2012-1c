package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;

public class IdGroupCollection {

    private ArrayList<Integer> idGroups;

    public IdGroupCollection() {
        this.idGroups = new ArrayList<Integer>();
    }

    public Integer addIdGroup(Integer number) throws Exception {
        if (!this.exists(number)) {
            this.idGroups.add(number);
            return number;
        } else {
            throw new Exception("Group's number: " + number + "already exists for this id");
        }
    }

    public ArrayList<Integer> getIdGroups() {
        return this.idGroups;
    }

    public void removeIdGroup(Integer number) throws Exception {

        if (this.exists(number)) {
            this.idGroups.remove(number);
        } else {
            throw new Exception("Group's number: " + number + "do not exists");
        }

    }

    public boolean exists(Integer idGroup) {
        class FieldCmpFunc extends Func<Integer, Integer, Boolean> {
            @Override
            public Boolean execute(Integer field, Integer n) {
                return field.equals(n);
            }
        }

        return IterableExtensions.firstOrDefault(this.idGroups, new FieldCmpFunc(), idGroup) != null;
    }


}
