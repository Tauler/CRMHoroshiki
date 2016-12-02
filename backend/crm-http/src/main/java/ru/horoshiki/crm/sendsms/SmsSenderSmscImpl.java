package ru.horoshiki.crm.sendsms;

import ru.horoshiki.crm.httpclient.HttpClientService;
import ru.horoshiki.crm.httpclient.HttpDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onyushkindv on 30.11.2016.
 */
public class SmsSenderSmscImpl implements SmsSender {
    private String login;
    private String password;
    private HttpDataSource httpDataSource;
    private HttpClientService httpClientService;

    public SmsSenderSmscImpl(String login, String password, HttpDataSource httpDataSource, HttpClientService httpClientService) {
        this.login = login;
        this.password = password;
        this.httpDataSource = httpDataSource;
        this.httpClientService = httpClientService;
    }


    @Override
    public boolean send(List<String> phones, String message) {
        try {
            String phonesStr = "";
            for(int i=0; i<phones.size(); i++){
                if(phones.size()>0 && i!=phones.size()-1) {
                    phonesStr += phones.get(i) + ";";
                }else{
                    phonesStr += phones.get(i);
                }
            }

            String resultString = httpClientService.httpPost("/sys/send.php", "login="+login+"&psw="+password+"&phones="+phonesStr+"&mes="+message+"&charset=utf-8&fmt=1", httpDataSource);
            String result = resultString.split(",")[0];

            if(result!="0")
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean send(String phone, String message) {
        ArrayList<String> phones = new ArrayList<>();
        phones.add(phone);
        return this.send(phones, message);
    }
}
