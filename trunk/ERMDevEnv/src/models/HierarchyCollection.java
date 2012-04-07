package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.UUID;

public class HierarchyCollection {

    public HierarchyCollection() {
        this.hierarchies = new ArrayList<Hierarchy>();
    }

    public Hierarchy createHierarchy(UUID generalEntityUUID, boolean exclusive, boolean total, ArrayList<UUID> childrenUUID) throws Exception {
        if (!existsHierarchyWithGeneralEntityUUID(generalEntityUUID)) {
            Hierarchy hierarchy = new Hierarchy(generalEntityUUID, total, exclusive, childrenUUID);
            this.hierarchies.add(hierarchy);
            return hierarchy;
        } else {
            throw new Exception("A hierarchy with general entity with uuid: " + generalEntityUUID + " already exists");
        }
    }

    public Hierarchy createHierarchy(UUID generalEntityUUID, boolean exclusive, boolean total) throws Exception {
        if (!existsHierarchyWithGeneralEntityUUID(generalEntityUUID)) {
            Hierarchy hierarchy = new Hierarchy(generalEntityUUID, total, exclusive);
            this.hierarchies.add(hierarchy);
            return hierarchy;
        } else {
            throw new Exception("A hierarchy with general entity with uuid: " + generalEntityUUID + " already exists");
        }
    }

    public Hierarchy addHierarchy(UUID hierarchyUUID, UUID generalEntityUUID, boolean exclusive, boolean total, ArrayList<UUID> childrenUUID) throws Exception {
        if (!existsHierarchy(hierarchyUUID)) {
            Hierarchy hierarchy = createHierarchy(generalEntityUUID, exclusive, total, childrenUUID);
            hierarchy.setHierarchyUUID(hierarchyUUID);
            return hierarchy;
        } else {
            throw new Exception("A hierarchy with uuid: " + hierarchyUUID + " already exists");
        }
    }

    public int count() {
        return this.hierarchies.size();
    }

    public Hierarchy getHierarchy(UUID hierarchyUUID) {
        return IterableExtensions.firstOrDefault(this.hierarchies, new HierarchyCmpFunc(), hierarchyUUID);
    }

    public Hierarchy getHierarchyWithGeneralEntityUUID(UUID generalEntityUUID) {
        return IterableExtensions.firstOrDefault(this.hierarchies, new GeneralHierarchyCmpFunc(), generalEntityUUID);
    }

    public void addChild(UUID generalEntityUUID, UUID childUUID) throws Exception {
        Hierarchy hierarchy = getHierarchyWithGeneralEntityUUID(generalEntityUUID);
        if (hierarchy != null) {
            hierarchy.addChildEntity(childUUID);
        } else {
            throw new Exception("Do not exists a general entity with uuid: " + generalEntityUUID);
        }
    }


    public void removeHierarchy(UUID hierarchyUUID) throws Exception {
        if (existsHierarchy(hierarchyUUID)) {
            this.hierarchies.remove(this.getHierarchy(hierarchyUUID));
        } else {
            throw new Exception("Do not exists a hierarchy with uuid: " + hierarchyUUID);
        }
    }

    public void removeHierarchyWithGeneralEntityUUID(UUID generalEntityUUID) throws Exception {
        if (existsHierarchyWithGeneralEntityUUID(generalEntityUUID)) {
            this.hierarchies.remove(this.getHierarchyWithGeneralEntityUUID(generalEntityUUID));
        } else {
            throw new Exception("Do not exists a hierarchy with general entity's uuid: " + generalEntityUUID);
        }
    }


    private ArrayList<Hierarchy> hierarchies;

    private boolean existsHierarchyWithGeneralEntityUUID(UUID generalEntityUUID) {
        return IterableExtensions.firstOrDefault(this.hierarchies, new GeneralHierarchyCmpFunc(), generalEntityUUID) != null;
    }

    private boolean existsHierarchy(UUID entityUUID) {
        return IterableExtensions.firstOrDefault(this.hierarchies, new HierarchyCmpFunc(), entityUUID) != null;
    }


    private class GeneralHierarchyCmpFunc extends Func<Hierarchy, UUID, Boolean> {
        @Override
        public Boolean execute(Hierarchy hierarchy, UUID uuid) {
            return hierarchy.getGeneralEntityUUID().equals(uuid);
        }

    }

    private class HierarchyCmpFunc extends Func<Hierarchy, UUID, Boolean> {
        @Override
        public Boolean execute(Hierarchy hierarchy, UUID uuid) {
            return hierarchy.getUUID().equals(uuid);
        }

    }
}
