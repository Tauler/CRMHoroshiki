package ru.horoshiki.crm.site.model.enums;

/**
 * Created by onyushkindv on 06.12.2016.
 */
public enum OrderConfirmType {
    CALL(0),
    SMS(1);

    private int value;

    OrderConfirmType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderConfirmType valueOf(Integer id) {
        if (id == null) return null;
        OrderConfirmType orderConfirmType = null;
        for (OrderConfirmType item : orderConfirmType.values()) {
            if (item.getValue()==id) {
                orderConfirmType = item;
                break;
            }
        }
        return orderConfirmType;
    }
}
