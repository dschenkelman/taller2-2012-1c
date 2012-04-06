package models.tests;

import junit.framework.Assert;
import models.IdGroupCollection;
import org.junit.Test;

public class IdGroupCollectionTestCase {

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

}
