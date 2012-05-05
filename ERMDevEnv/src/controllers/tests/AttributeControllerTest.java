package controllers.tests;

import controllers.AttributeController;
import controllers.tests.mocks.*;
import infrastructure.IterableExtensions;
import junit.framework.Assert;
import models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttributeControllerTest {

    private MockAttributeView mockAttributeView;
    private MockProjectContext mockProjectContext;
    private AttributeController attributeController;
    private MockKeyControllerFactory mockKeyControllerFactory;
    private MockKeyController mockKeyController;

    @Before
    public void setUp() throws Exception {
        mockAttributeView = new MockAttributeView();
        mockProjectContext = new MockProjectContext();
        mockKeyControllerFactory = new MockKeyControllerFactory();
        mockKeyController = new MockKeyController();
        attributeController = new AttributeController(mockProjectContext, new ArrayList<Attribute>(),mockAttributeView, mockKeyControllerFactory);
    }

    @Test
    public void TestCreate() {
        Assert.assertEquals(mockAttributeView.getController(), attributeController);
        Assert.assertEquals(0, IterableExtensions.count(attributeController.getAttributes()));
    }

    @Test
    public void TestSelectKeys() {
        mockKeyControllerFactory.setKeyController(mockKeyController);
        attributeController.selectKeys();
        Assert.assertTrue(mockKeyControllerFactory.createCalled());
        Assert.assertEquals(attributeController.getAttributes(), mockKeyController.getKeys());
    }

    @Test
    public void TestHandleEvent() {
        HashMap<Integer, List<IKey>> keys = new HashMap<Integer, List<IKey>>();
        List<IKey> list = new ArrayList<IKey>();

        Attribute attribute = new Attribute("Name");
        attribute.setIdGroup(new IdGroupCollection());
        list.add(attribute);

        attribute = new Attribute("azsdasd");
        attribute.setIdGroup(new IdGroupCollection());
        list.add(attribute);

        attribute = new Attribute("asdasd");
        attribute.setIdGroup(new IdGroupCollection());
        list.add(attribute);

        keys.put(0, list);

        attribute = new Attribute("asdasdasdasda");
        attribute.setIdGroup(new IdGroupCollection());
        list.add(attribute);
        keys.put(1, list);

        attributeController.handleEvent(keys);

        for (Integer idGroup : keys.keySet()) {
            for (IKey key : list) {
                Assert.assertTrue(key.getIdGroup().exists(idGroup));
            }
        }
    }

    @Test
    public void testAddAttribute() {
        Assert.assertEquals(0, IterableExtensions.count(this.attributeController.getAttributes()));
        try {
            this.attributeController.addNewAttribute("att", false, new Cardinality(1, 1), AttributeType.calculated, "aaksda");
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(1, IterableExtensions.count(this.attributeController.getAttributes()));

        Attribute attribute = this.attributeController.getAttributes().iterator().next();
        Assert.assertEquals("att",attribute.getName());
        Assert.assertEquals(false,attribute.isKey());
        Assert.assertEquals(AttributeType.calculated,attribute.getType());

    }
}
