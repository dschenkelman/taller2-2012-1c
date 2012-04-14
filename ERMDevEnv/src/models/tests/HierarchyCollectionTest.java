package models.tests;

import infrastructure.IterableExtensions;
import junit.framework.Assert;
import models.Hierarchy;
import models.HierarchyCollection;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

public class HierarchyCollectionTest {

    @Test
    public void testCreateHierarchyCollection() {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();
        Assert.assertEquals(0, hierarchyCollection.count());
    }

    @Test
    public void testCreateHierarchyOnHierarchyCollection() {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();

        UUID generalEntityUUID = UUID.randomUUID();
        boolean exclusive = true;
        boolean total = true;

        Hierarchy hierarchy = hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        Assert.assertEquals(generalEntityUUID, hierarchy.getGeneralEntityUUID());

        UUID hierarchyUUID = null;
        try {
            hierarchyUUID = UUID.randomUUID();
            hierarchy = hierarchyCollection.createHierarchy(hierarchyUUID, UUID.randomUUID(), exclusive, total, new ArrayList<UUID>());
            Assert.assertEquals(hierarchyUUID, hierarchy.getUUID());
            Assert.assertEquals(exclusive, hierarchy.isExclusive());
            Assert.assertEquals(total, hierarchy.isTotal());
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertEquals(2, hierarchyCollection.count());
    }

    @Test(expected = Exception.class)
    public void testCreateHierarchyOnHierarchyCollectionFail() throws Exception {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();

        UUID generalEntityUUID = UUID.randomUUID();
        UUID hierarchyUUID = UUID.randomUUID();
        boolean exclusive = true;
        boolean total = true;

        hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        hierarchyCollection.createHierarchy(hierarchyUUID, generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        hierarchyCollection.createHierarchy(hierarchyUUID, UUID.randomUUID(), exclusive, total, new ArrayList<UUID>());

    }

    @Test
    public void testGetOnHierarchyCollection() {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();

        UUID generalEntityUUID = UUID.randomUUID();
        boolean exclusive = true;
        boolean total = true;

        hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        Assert.assertEquals(generalEntityUUID, hierarchyCollection.getHierarchiesWithGeneralEntityUUID(generalEntityUUID).iterator().next().getGeneralEntityUUID());
        Assert.assertEquals(3, IterableExtensions.count(hierarchyCollection.getHierarchiesWithGeneralEntityUUID(generalEntityUUID)));


        try {
            UUID hierarchyUUID = UUID.randomUUID();
            hierarchyCollection.createHierarchy(hierarchyUUID, UUID.randomUUID(), exclusive, total, new ArrayList<UUID>());
            Assert.assertEquals(hierarchyUUID, hierarchyCollection.getHierarchy(hierarchyUUID).getUUID());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testAddChildToHierarchyOnHierarchyCollection() {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();

        UUID generalEntityUUID = UUID.randomUUID();
        UUID childUUID = UUID.randomUUID();
        boolean exclusive = true;
        boolean total = true;

        try {
            Hierarchy hierarchy = hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total);
            hierarchyCollection.addChild(hierarchy.getUUID(), childUUID);
            hierarchy.hasChild(childUUID);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = Exception.class)
    public void testAddChildToHierarchyOnHierarchyCollectionFail() throws Exception {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();

        UUID generalEntityUUID = UUID.randomUUID();
        UUID childUUID = UUID.randomUUID();
        boolean exclusive = true;
        boolean total = true;

        hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total);
        hierarchyCollection.addChild(UUID.randomUUID(), childUUID);
    }

    @Test
    public void testRemoveHierarchy() {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();

        UUID generalEntityUUID = UUID.randomUUID();
        UUID hierarchyUUID = UUID.randomUUID();
        boolean exclusive = true;
        boolean total = true;

        Hierarchy hierarchy = hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        Assert.assertEquals(generalEntityUUID, hierarchy.getGeneralEntityUUID());

        try {
            hierarchy = hierarchyCollection.createHierarchy(hierarchyUUID, UUID.randomUUID(), exclusive, total, new ArrayList<UUID>());
            Assert.assertEquals(hierarchyUUID, hierarchy.getUUID());
        } catch (Exception e) {
            Assert.fail();
        }

        try {
            hierarchyCollection.removeHierarchy(hierarchyUUID);
        } catch (Exception e) {
            Assert.fail();
        }

    }

    @Test(expected = Exception.class)
    public void testRemoveHierarchyFail() throws Exception {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();

        UUID generalEntityUUID = UUID.randomUUID();
        UUID hierarchyUUID = UUID.randomUUID();
        boolean exclusive = true;
        boolean total = true;

        hierarchyCollection.removeHierarchy(hierarchyUUID);

        Hierarchy hierarchy = hierarchyCollection.createHierarchy(generalEntityUUID, exclusive, total, new ArrayList<UUID>());
        Assert.assertEquals(generalEntityUUID, hierarchy.getGeneralEntityUUID());

        try {
            hierarchy = hierarchyCollection.createHierarchy(hierarchyUUID, UUID.randomUUID(), exclusive, total, new ArrayList<UUID>());
            Assert.assertEquals(generalEntityUUID, hierarchy.getGeneralEntityUUID());
        } catch (Exception e) {
            Assert.fail();
        }

        try {
            hierarchyCollection.removeHierarchy(hierarchyUUID);
        } catch (Exception e) {
            Assert.fail();
        }


    }
}