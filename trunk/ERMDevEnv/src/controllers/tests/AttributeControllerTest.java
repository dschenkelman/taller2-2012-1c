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
        attributeController = new AttributeController(mockProjectContext, new ArrayList<Attribute>(),mockAttributeView);
    }

    @Test
    public void TestCreate() {
        Assert.assertEquals(mockAttributeView.getController(), attributeController);
        Assert.assertEquals(0, IterableExtensions.count(attributeController.getAttributes()));
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
