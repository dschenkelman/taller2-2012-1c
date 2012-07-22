package models;

public enum AttributeType {
    characterization("Characterization", 0),
    calculated("Calculated", 1),
    copy("Copy", 2);

    private String description;
    private int id;

    AttributeType(String description, int id) {
        this.description = description;
        this.id = id;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }


}

