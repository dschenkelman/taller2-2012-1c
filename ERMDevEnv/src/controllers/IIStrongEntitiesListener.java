package controllers;

import models.IStrongEntity;

import java.util.List;

public interface IIStrongEntitiesListener {

    void handleEvent(List<IStrongEntity> param);

}
