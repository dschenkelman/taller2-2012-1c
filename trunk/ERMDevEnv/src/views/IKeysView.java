package views;

import controllers.IKeysController;
import models.IKey;
import models.IdGroup;


import java.util.HashMap;
import java.util.List;

public interface IKeysView {

    void showView();

    void setController(IKeysController controller);

    public void setPossibleKeys(Iterable<IKey> keys);

    public IdGroup getIdGroupSelected();

    public IKey getKeySelectedToAdd();

    public IKey getKeySelectedToRemove();

    public Iterable<IKey> getKeysOfIdGroupSelected();
}
