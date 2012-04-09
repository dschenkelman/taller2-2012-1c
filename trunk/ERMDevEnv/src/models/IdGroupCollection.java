package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;

public class IdGroupCollection {


    public IdGroupCollection() {
        this.idGroups = new ArrayList<Integer>();
    }

    public Iterable<Integer> getIdGroups(){
        return this.idGroups;
    }

    public Integer addIdGroup(Integer number) throws Exception {
        if (!this.exists(number)) {
            this.idGroups.add(number);
            return number;
        } else {
            throw new Exception("Group's number: " + number + "already exists for this id");
        }
    }

    public void removeIdGroup(Integer number) throws Exception {

        if (this.exists(number)) {
            this.idGroups.remove(number);
        } else {
            throw new Exception("Group's number: " + number + "do not exists");
        }

    }

    public boolean exists(Integer idGroup) {
        return IterableExtensions.firstOrDefault(this.idGroups, new IntegerCmpFunc(), idGroup) != null;
    }
    
    public int count(){
        return this.idGroups.size();
    }



    private ArrayList<Integer> idGroups;

    private class IntegerCmpFunc extends Func<Integer, Integer, Boolean> {
        @Override
        public Boolean execute(Integer number, Integer n) {
            return number.equals(n);
        }
    }


}
