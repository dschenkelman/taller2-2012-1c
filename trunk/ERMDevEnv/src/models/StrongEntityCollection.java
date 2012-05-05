package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class StrongEntityCollection implements Iterable<IStrongEntity>{

    private List<IStrongEntity> strongEntities;

    public StrongEntityCollection() {
        this.strongEntities = new ArrayList<IStrongEntity>();
    }

    public IStrongEntity getStrongEntity(UUID uuid) {
        return IterableExtensions.firstOrDefault(this, new EntityCmpUUIDFunction(), uuid);
    }

    public void addStrongEntity(IStrongEntity strongEntity) throws Exception {
        if (this.getStrongEntity(strongEntity.getId()) == null) {
            this.strongEntities.add(strongEntity);
        } else {
            throw new Exception("An strong Entity with guid: " + strongEntity.getId() + " already exists");
        }
    }

    public int count() {
        return IterableExtensions.count(this);
    }

    @Override
    public Iterator<IStrongEntity> iterator() {
        return this.strongEntities.iterator();
    }

    public IStrongEntity getStrongEntity(String strongEntity) {
        return IterableExtensions.firstOrDefault(this, new EntityCmpNameFunction(), strongEntity);
    }

    private class EntityCmpUUIDFunction extends Func<IStrongEntity, UUID, Boolean> {
        @Override
        public Boolean execute(IStrongEntity strongEntity, UUID uuid) {
            return strongEntity.getId().equals(uuid);
        }
    }

    private class EntityCmpNameFunction extends Func<IStrongEntity,String,Boolean> {
        @Override
        public Boolean execute(IStrongEntity iStrongEntity, String s) {
            return iStrongEntity.getName().equals(s);
        }
    }
}
