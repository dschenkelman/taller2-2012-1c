package models.tests;

import junit.framework.Assert;
import models.IdGroup;
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
            idGroupCollection.addIdGroup(new IdGroup(0,false));
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(true, idGroupCollection.exists(0));
    }

    @Test
    public void testIdGroupCollectionCreate() {
        IdGroupCollection idGroupCollection = new IdGroupCollection();

        try {
            idGroupCollection.addIdGroup(new IdGroup(0, false));
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(1, idGroupCollection.count());

        try {
            idGroupCollection.addIdGroup(new IdGroup(1,false));
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(2, idGroupCollection.count());
    }


    @Test(expected = Exception.class)
    public void testAddIdGroupCollectionAddFail() throws Exception {
        IdGroupCollection idGroupCollection = new IdGroupCollection();
        try {
            idGroupCollection.addIdGroup(new IdGroup(0,false));
        } catch (Exception e) {
            Assert.fail();
        }
        idGroupCollection.addIdGroup(new IdGroup(0,false));
    }

    @Test
    public void testRemove() {
        IdGroupCollection idGroupCollection = new IdGroupCollection();

        IdGroup idGroup = new IdGroup(0,false);
        try {
            idGroupCollection.addIdGroup(idGroup);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(true, idGroupCollection.exists(0));
        try {
            idGroupCollection.removeIdGroup(idGroup);
            Assert.assertEquals(false, idGroupCollection.exists(0));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = Exception.class)
    public void testRemoveFail() throws Exception {

        IdGroupCollection idGroupCollection = new IdGroupCollection();
        idGroupCollection.removeIdGroup(new IdGroup(0,false));
    }

}
