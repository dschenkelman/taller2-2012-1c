package models.tests;

import junit.framework.Assert;
import models.IdGroupCollection;
import org.junit.Test;

public class IdGroupCollectionTestCase {

    @Test
    public void testExists(){
        IdGroupCollection idGroupCollection = new IdGroupCollection();
        Assert.assertEquals(false, idGroupCollection.exists(0));
    }

    @Test
    public void testIdGroupCollectionAdd() {
        IdGroupCollection idGroupCollection = new IdGroupCollection();

        try {
            idGroupCollection.addIdGroup(0);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(true, idGroupCollection.exists(0));
    }

    @Test
    public void testIdGroupCollectionCreate() {
        IdGroupCollection idGroupCollection = new IdGroupCollection();

        try {
            idGroupCollection.addIdGroup(0);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(1, idGroupCollection.count());

        try {
            idGroupCollection.addIdGroup(1);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(2, idGroupCollection.count());
    }


    @Test(expected = Exception.class)
    public void testAddIdGroupCollectionAddFail() throws Exception {
        IdGroupCollection idGroupCollection = new IdGroupCollection();
        try {
            idGroupCollection.addIdGroup(0);
        } catch (Exception e) {
            Assert.fail();
        }
        idGroupCollection.addIdGroup(0);
    }

    @Test
    public void testRemove() {
        IdGroupCollection idGroupCollection = new IdGroupCollection();

        try {
            idGroupCollection.addIdGroup(0);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(true, idGroupCollection.exists(0));
        try {
            idGroupCollection.removeIdGroup(0);
            Assert.assertEquals(false, idGroupCollection.exists(0));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = Exception.class)
    public void testRemoveFail() throws Exception {

        IdGroupCollection idGroupCollection = new IdGroupCollection();
        idGroupCollection.removeIdGroup(0);
    }

}
