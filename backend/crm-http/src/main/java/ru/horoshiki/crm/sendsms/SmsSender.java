package ru.horoshiki.crm.sendsms;

import java.util.List;

/**
 * Created by onyushkindv on 30.11.2016.
 */
public interface SmsSender {
    boolean send(List<String> phones, String message);
}
