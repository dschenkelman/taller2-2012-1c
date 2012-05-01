package controllers.tests;

import controllers.EntityController;
import controllers.tests.mocks.*;
import junit.framework.Assert;
import models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EntityControllerTest {

    private MockAttributeController mockAttributeController;
    private MockEntityView mockEntityView;
    private MockProjectContext mockProjectContext;
    private MockIEventListener mockEntityCreatedListener;
    private EntityController entityController;
    private static final String ENTITY_NAME = "NAME";
    private static final String ATT_NAME_1 = "ADSADS";
    private static final String ATT_NAME_2 = "SSADSADS";
    private static final String ATT_NAME_3 = "ADSSSSADS";
    private static final String ATT_NAME_4 = "ADSASADDS";

    @Test
    public void TestCreation() {
        Assert.assertEquals(entityController, this.mockEntityView.getController());
    }

    @Test
    public void TestCreate() {
        entityController.create();
        Assert.assertTrue(this.mockEntityView.addViewWasCall);
        Assert.assertTrue(this.mockEntityView.isVisible());
    }

    @Test
    public void TestValidateName(){
        EntityCollection entityCollection = new EntityCollection();
        entityCollection.add(ENTITY_NAME, EntityType.Domain);

        this.mockProjectContext.setEntityCollection(entityCollection);
        this.entityController = new EntityController(mockProjectContext,mockEntityView,mockAttributeController);

        mockEntityView.setEntityName("");
        Assert.assertFalse(this.entityController.addEntity());

        mockEntityView.setEntityName(ENTITY_NAME);
        Assert.assertFalse(this.entityController.addEntity());

        mockEntityView.setEntityName("asdadasdasdas");
        Assert.assertTrue(this.entityController.addEntity());

    }

    @Test
    public void TestAddEntity() {
        EntityCollection entityCollection = new EntityCollection();
        entityCollection.add(ENTITY_NAME, EntityType.Domain);
        List<Attribute> list = new ArrayList<Attribute>();
        list.add(new Attribute(ATT_NAME_1));
        list.add(new Attribute(ATT_NAME_2));
        list.add(new Attribute(ATT_NAME_3));
        list.add(new Attribute(ATT_NAME_4));
        mockAttributeController.setAttributes(list);
        
        mockEntityCreatedListener = new MockIEventListener();
        this.entityController.addSubscriber(this.mockEntityCreatedListener);
        this.mockEntityView.setEntityName("");
        Assert.assertFalse(this.entityController.addEntity());
        Assert.assertFalse(this.mockEntityCreatedListener.called);
        
        this.mockProjectContext.setEntityCollection(entityCollection);
        this.entityController = new EntityController(mockProjectContext,mockEntityView,mockAttributeController);
        this.mockEntityView.setEntityName("sdadasd");
        this.entityController.addSubscriber(this.mockEntityCreatedListener);
        Assert.assertTrue(this.entityController.addEntity());
        Assert.assertTrue(this.mockEntityCreatedListener.called);

        this.mockEntityView.setEntityName(ENTITY_NAME);
        Assert.assertFalse(this.entityController.addEntity());
        this.mockEntityCreatedListener.called = false;
        Assert.assertFalse(this.mockEntityCreatedListener.called);
        
        Entity entity = (Entity) mockEntityCreatedListener.get()[0];
        AttributeCollection attributeCollection = entity.getAttributes();
        Assert.assertNotNull(attributeCollection.getAttribute(ATT_NAME_1));
        Assert.assertNotNull(attributeCollection.getAttribute(ATT_NAME_2));
        Assert.assertNotNull(attributeCollection.getAttribute(ATT_NAME_3));
        Assert.assertNotNull(attributeCollection.getAttribute(ATT_NAME_4));



    }

    @Before
    public void setUp() throws Exception {
        mockAttributeController = new MockAttributeController();
        mockEntityView = new MockEntityView();
        mockProjectContext = new MockProjectContext();
        mockEntityCreatedListener = new MockIEventListener();
        entityController = new EntityController(mockProjectContext, mockEntityView, mockAttributeController);
    }
}
