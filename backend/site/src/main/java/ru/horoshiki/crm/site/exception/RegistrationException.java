package ru.horoshiki.crm.site.exception;

/**
 * Created by onyushkindv on 26.09.2014.
 */
public class RegistrationException extends Exception {
    public RegistrationException(String msg) {
        super(msg);
    }
    public RegistrationException(){
        super("При регистрации произошла ошибка.");
    }
}
