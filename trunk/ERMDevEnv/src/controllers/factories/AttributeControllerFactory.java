package controllers.factories;

import application.Bootstrapper;
import controllers.AttributeController;
import controllers.IAttributeController;
import infrastructure.IProjectContext;
import infrastructure.IterableExtensions;
import models.Attribute;
import models.AttributeCollection;
import org.picocontainer.MutablePicoContainer;
import views.AttributeView;

import java.util.List;

public class AttributeControllerFactory implements IAttributeControllerFactory{
    @Override
    public IAttributeController create(AttributeCollection possibleAttributes) {
        Bootstrapper bootstrapper = new Bootstrapper();
        MutablePicoContainer container = bootstrapper.getContainer();
        List<Attribute> attributeList = IterableExtensions.getListOf(possibleAttributes);
        return new AttributeController(container.getComponent(IProjectContext.class),attributeList,new AttributeView(),container.getComponent(IKeysControllerFactory.class));
    }
}
