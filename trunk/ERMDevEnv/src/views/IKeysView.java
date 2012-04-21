package views;

import models.IKey;
import controllers.KeysController;

import java.util.HashMap;
import java.util.List;

public interface IKeysView {
    void show(Iterable<IKey> possibleKeys);

    void setController(KeysController controller);

    HashMap<Integer,List<IKey>> getKeys();
}
