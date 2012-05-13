package views;

import controllers.IKeysController;
import models.IKey;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gaston_festa
 * Date: 5/13/12
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeyView implements IKeysView {
    @Override
    public void show() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setController(IKeysController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HashMap<Integer, List<IKey>> getKeys() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPossibleKeys(Iterable<IKey> keys) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
