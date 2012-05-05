package views;

import controllers.IStrongEntityController;
import models.IStrongEntity;

import java.util.List;

public interface IStrongEntityView {
    public void setController(IStrongEntityController strongEntityController);

    public void setPossibleStrongEntities(Iterable<IStrongEntity> possibleStrongEntities);

    public void setStrongEntities(List<IStrongEntity> strongEntities);
}
