package controllers.factories;

import application.Bootstrapper;
import controllers.IKeysController;
import controllers.KeysController;
import infrastructure.IProjectContext;
import models.IKey;
import org.picocontainer.MutablePicoContainer;
import views.KeyView;

import java.util.List;

public class KeyControllerFactory implements IKeysControllerFactory{

    @Override
    public IKeysController create(List<IKey> keys) {
        Bootstrapper bootstrapper = new Bootstrapper();
        MutablePicoContainer container = bootstrapper.getContainer();
        return new KeysController(container.getComponent(IProjectContext.class),new KeyView(),keys);
    }
}
