package controllers.tests.mocks;

import java.util.List;

import models.IStrongEntity;
import views.IStrongEntityView;
import controllers.IStrongEntityController;

public class MockStrongEntityController implements IStrongEntityController {
    private boolean getStrongEntityViewCalled = false;
    private Iterable<IStrongEntity> strongEntities;

    @Override
    public IStrongEntityView getStrongEntityView() {
        this.getStrongEntityViewCalled = true;
        return new IStrongEntityView() {
            @Override
            public void setController(IStrongEntityController strongEntityController) {

            }

            @Override
            public void setPossibleStrongEntities(Iterable<IStrongEntity> possibleStrongEntities) {
            }

            @Override
            public void setStrongEntities(List<IStrongEntity> strongEntities) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

    @Override
    public Iterable<IStrongEntity> getStrongEntities() {
        return this.strongEntities;
    }

    @Override
    public void addStrongEntity(IStrongEntity strongEntity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setStrongEntityVIew(IStrongEntityView strongEntityView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isGetStrongEntityViewCalled() {
        return getStrongEntityViewCalled;
    }

    public void setStrongEntities(Iterable<IStrongEntity> strongEntities) {
        this.strongEntities = strongEntities;
    }
}
