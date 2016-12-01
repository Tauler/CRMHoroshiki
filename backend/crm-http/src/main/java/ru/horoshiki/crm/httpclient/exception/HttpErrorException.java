package ru.horoshiki.crm.httpclient.exception;

/**
 * Created by onyushkindv on 27.04.2016.
 */
public class HttpErrorException extends Exception {
    public HttpErrorException(String msg) {
        super(msg);
    }
    public HttpErrorException() {}
}
