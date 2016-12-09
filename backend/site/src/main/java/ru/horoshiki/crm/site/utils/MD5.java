/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.horoshiki.crm.site.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author donyushkin
 */
public class MD5 {
    public static String createMD5(String raw)
   {
       String output = null;
       try
       {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(raw.getBytes(), 0, raw.length());
           output = new BigInteger(1, md.digest()).toString(16);
       }
       catch (NoSuchAlgorithmException e)
       {
           e.printStackTrace();
       }
       return output;
   }
}
