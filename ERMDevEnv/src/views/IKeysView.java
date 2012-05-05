package views;

import controllers.IKeysController;
import models.IKey;
import models.IStrongEntity;


import java.util.HashMap;
import java.util.List;

public interface IKeysView {
    void show();

    void setController(IKeysController controller);

    HashMap<Integer,List<IKey>> getKeys();

    public void setPossibleKeys(Iterable<IKey> keys);
}
