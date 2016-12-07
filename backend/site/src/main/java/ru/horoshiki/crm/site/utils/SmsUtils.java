package ru.horoshiki.crm.site.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by onyushkindv on 07.12.2016.
 */
public class SmsUtils {
    public static String generateCode(){
        int min = 1000;
        int max = 9999;
        return String.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1));
    }
}
