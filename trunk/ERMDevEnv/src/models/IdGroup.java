package models;

public class IdGroup {
    Integer number;
    Boolean isKey;

    public IdGroup(Integer number, Boolean key) {
        this.number = number;
        this.isKey = key;
    }

    public Boolean isKey() {
        return isKey;
    }

    public void isKey(Boolean isKey) {
        this.isKey = isKey;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Id Group: " + this.number;
    }
}
