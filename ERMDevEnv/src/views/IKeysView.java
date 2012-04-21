package views;

import controllers.KeysController;
import models.INameable;

public interface IKeysView {
    void show(Iterable<INameable> possibleKeys);

    void setController(KeysController controller);
}
