package ru.horoshiki.crm.site.model.enums;

/**
 * Created by onyushkindv on 06.12.2016.
 */
public enum Sex {
    MALE(0),
    FEMALE(1);

    private int value;

    Sex(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Sex valueOf(Integer id) {
        if (id == null) return null;
        Sex sex = null;
        for (Sex item : sex.values()) {
            if (item.getValue()==id) {
                sex = item;
                break;
            }
        }
        return sex;
    }
}
