package models;

import java.util.UUID;

public interface IKey {
    public String getName();

    public UUID getId();

    public void isKey(boolean value);

    public boolean isKey();

    public IdGroupCollection getIdGroup();
}
