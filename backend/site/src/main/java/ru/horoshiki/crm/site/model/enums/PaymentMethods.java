package ru.horoshiki.crm.site.model.enums;

/**
 * Created by onyushkindv on 01.12.2016.
 */
public enum PaymentMethods {

    CARD(0),
    CASH(1),
    DELIVERY_CARD(2);

    private int value;

    PaymentMethods(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PaymentMethods valueOf(Integer id) {
        if (id == null) return null;
        PaymentMethods paymentMethods = null;
        for (PaymentMethods item : PaymentMethods.values()) {
            if (item.getValue()==id) {
                paymentMethods = item;
                break;
            }
        }
        return paymentMethods;
    }
}
