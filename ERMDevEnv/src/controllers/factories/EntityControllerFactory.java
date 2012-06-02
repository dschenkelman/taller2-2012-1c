package controllers.factories;

import application.Bootstrapper;
import controllers.EntityController;
import controllers.IEntityController;
import infrastructure.IProjectContext;
import models.Entity;
import org.picocontainer.MutablePicoContainer;
import views.EntityView;

public class EntityControllerFactory implements IEntityControllerFactory {
    @Override
    public IEntityController create() {
        Bootstrapper bootstrapper = new Bootstrapper();
        MutablePicoContainer container = bootstrapper.getContainer();
        return new EntityController(container.getComponent(IProjectContext.class), new Entity(""), new EntityView(), container.getComponent(IAttributeControllerFactory.class), container.getComponent(IKeysControllerFactory.class));
    }

    @Override
    public IEntityController create(Entity entity) {
        Bootstrapper bootstrapper = new Bootstrapper();
        MutablePicoContainer container = bootstrapper.getContainer();
        return new EntityController(container.getComponent(IProjectContext.class), entity, new EntityView(), container.getComponent(IAttributeControllerFactory.class), container.getComponent(IKeysControllerFactory.class));
    }
}
