package ru.horoshiki.crm.site.model.dto;

/**
 * Created by bogdanovkp on 27.08.2015.
 */
public class BackendData<T> {
    private boolean success;
    private String reason;
    private T data;

    public static<T> BackendData success(T data){
        BackendData backendData = new BackendData();
        backendData.setSuccess(true);
        backendData.setData(data);
        backendData.setReason("");
        return backendData;
    }
    public static  BackendData error(String reason){
        BackendData backendData = new BackendData();
        backendData.setSuccess(false);
        backendData.setData(null);
        backendData.setReason(reason);
        return backendData;
    }

    public T getData() {
        return data;
    }
    public String getReason() { return reason; }
    public boolean isSuccess() {
        return success;
    }

    private void setSuccess(boolean success) {
        this.success = success;
    }
    private void setReason(String reason) {
        this.reason = reason;
    }
    private void setData(T data) {
        this.data = data;
    }
}
