package models;

import java.util.UUID;

public interface IKey {
    
    public String getName();
    public UUID getOwnerId();
    public UUID getId();
}
