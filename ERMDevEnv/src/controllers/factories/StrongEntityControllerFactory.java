package controllers.factories;

import application.Bootstrapper;
import controllers.IStrongEntityController;
import controllers.StrongEntityController;
import infrastructure.IProjectContext;
import infrastructure.IterableExtensions;
import models.IStrongEntity;
import models.StrongEntityCollection;
import org.picocontainer.MutablePicoContainer;
import views.StrongEntityView;

import java.util.List;

public class StrongEntityControllerFactory implements IStrongEntityControllerFactory{
    @Override
    public IStrongEntityController create(StrongEntityCollection strongEntities) {
        Bootstrapper bootstrapper = new Bootstrapper();
        MutablePicoContainer container = bootstrapper.getContainer();
        List<IStrongEntity> strongEntitiesList = IterableExtensions.getListOf(strongEntities);
        return new StrongEntityController(container.getComponent(IProjectContext.class),new StrongEntityView(),strongEntitiesList);
    }
}
