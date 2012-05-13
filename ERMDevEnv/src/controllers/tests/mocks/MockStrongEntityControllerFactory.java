package controllers.tests.mocks;

import controllers.IStrongEntityController;
import controllers.StrongEntityController;
import controllers.factories.IStrongEntityControllerFactory;
import models.StrongEntityCollection;

/**
 * Created by IntelliJ IDEA.
 * User: gaston_festa
 * Date: 5/13/12
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockStrongEntityControllerFactory implements IStrongEntityControllerFactory{
    private IStrongEntityController strongEntityController;

    @Override
    public IStrongEntityController create(StrongEntityCollection strongEntities) {
        return this.strongEntityController;
    }

    public void setStrongEntityController(IStrongEntityController strongEntityController) {
        this.strongEntityController = strongEntityController;
    }
}
