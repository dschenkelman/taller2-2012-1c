package models.tests;

import infrastructure.Func;
import infrastructure.IterableExtensions;
import junit.framework.Assert;
import models.Entity;
import models.IStrongEntity;
import models.StrongEntityCollection;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class StrongEntityCollectionTest {

    private StrongEntityCollection strongEntityCollection;

    @Test
    public void CreationTest() {
        Assert.assertEquals(0, strongEntityCollection.count());
    }

    @Test
    public void addStrongEntity() {
        Entity entity = new Entity("name");
        Assert.assertNull(IterableExtensions.firstOrDefault(strongEntityCollection, new Cmp(), entity.getId()));
        try {
            strongEntityCollection.addStrongEntity(entity);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotNull(IterableExtensions.firstOrDefault(strongEntityCollection, new Cmp(), entity.getId()));
    }

    @Test(expected = Exception.class)
    public void addThrowException() throws Exception {

        Entity entity = new Entity("name");
        Assert.assertNull(IterableExtensions.firstOrDefault(strongEntityCollection, new Cmp(), entity.getId()));
        try {
            strongEntityCollection.addStrongEntity(entity);
        } catch (Exception e) {
            Assert.fail();
        }
        strongEntityCollection.addStrongEntity(entity);

    }

    @Test
    public void getTest() {
        Entity entity = new Entity("name");
        Assert.assertNull(IterableExtensions.firstOrDefault(strongEntityCollection, new Cmp(), entity.getId()));
        try {
            strongEntityCollection.addStrongEntity(entity);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotNull(strongEntityCollection.getStrongEntity(entity.getId()));
        Assert.assertNotNull(strongEntityCollection.getStrongEntity(entity.getName()));
    }

    @Before
    public void before() {
        strongEntityCollection = new StrongEntityCollection();
    }

    private class Cmp extends Func<IStrongEntity, UUID, Boolean> {
        @Override
        public Boolean execute(IStrongEntity iStrongEntity, UUID uuid) {
            return iStrongEntity.getId().equals(uuid);
        }
    }
}

