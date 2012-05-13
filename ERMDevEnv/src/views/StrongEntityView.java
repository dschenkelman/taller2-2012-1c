package views;

import controllers.IStrongEntityController;
import models.IStrongEntity;

import java.util.ArrayList;
import java.util.List;

public class StrongEntityView implements IStrongEntityView{

    private Iterable<IStrongEntity> possibleStrongEntities;
    private IStrongEntityController strongEntityController;
    private List<IStrongEntity> strongEntities;

    public StrongEntityView(){
        this.possibleStrongEntities = new ArrayList<IStrongEntity>();
        this.strongEntities = new ArrayList<IStrongEntity>();
    }

    @Override
    public void setController(IStrongEntityController strongEntityController) {
        this.strongEntityController = strongEntityController;
    }


    @Override
    public void setPossibleStrongEntities(Iterable<IStrongEntity> possibleStrongEntities) {
        this.possibleStrongEntities = possibleStrongEntities;
    }

    @Override
    public void setStrongEntities(List<IStrongEntity> strongEntities) {
        this.strongEntities = strongEntities;
    }
}
