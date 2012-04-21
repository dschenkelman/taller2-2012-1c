package controllers.tests;

import controllers.AttributeController;
import controllers.IKeysController;
import controllers.tests.mocks.*;
import infrastructure.IControllerFactory;
import junit.framework.Assert;
import models.Attribute;
import models.IKey;
import models.IdGroupCollection;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttributeControllerTest {

    private MockAttributeView mockAttributeView;
    private MockProjectContext mockProjectContext;
    private MockIEventListener mockEntityCreatedListener;
    private MockEntityController mockEntityController;
    private AttributeController attributeController;
    private static final String ENTITY_NAME = "NAME";
    private MockKeyControllerFactory mockKeyControllerFactory;
    private MockKeyController mockKeyController;

    @Before
    public void setUp() throws Exception {
        mockAttributeView = new MockAttributeView();
        mockProjectContext = new MockProjectContext();
        mockEntityCreatedListener = new MockIEventListener();
        mockEntityController = new MockEntityController();
        mockKeyControllerFactory = new MockKeyControllerFactory();
        mockKeyController = new MockKeyController();
        attributeController = new AttributeController(mockProjectContext, mockAttributeView, mockKeyControllerFactory);
    }

    @Test
    public void TestCreate() {
        Assert.assertEquals(mockAttributeView.getController(), attributeController);
    }

    @Test
    public void TestSelectKeys() {
        List<Attribute> list = new ArrayList<Attribute>();
        mockAttributeView.setAttribute(list);
        mockKeyControllerFactory.setKeyController(mockKeyController);
        attributeController.selectKeys();
        Assert.assertTrue(mockKeyControllerFactory.createCalled());
        Assert.assertEquals(list, mockKeyController.getKeys());
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
                Assert.assertTrue(((Attribute) key).getIdGroup().exists(idGroup));
            }
        }
    }
}
