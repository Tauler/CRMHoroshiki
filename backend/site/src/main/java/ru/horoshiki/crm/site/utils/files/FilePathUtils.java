/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.horoshiki.crm.site.utils.files;

import ru.horoshiki.crm.site.utils.MD5;

import javax.naming.NamingException;
import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author donyushkin
 */
public class FilePathUtils {

    public static final int COUNT_FOLDER_LEVEL_1 = 10;
    public static final int COUNT_FOLDER_LEVEL_2 = 10;
    public static final String FOLDER_NAME_LEVEL_1 = "lo_";
    public static final String FOLDER_NAME_LEVEL_2 = "lt_";
    public static final String IMAGE_TYPE = "jpg";
    public static final String NAME_FOLDER_FILE = "files";


    private static Random random = new Random();

    public static String getImagePath(String basePaht) throws Exception {
        int folderIdLavel1 = Math.abs(random.nextInt()) % COUNT_FOLDER_LEVEL_1;
        int folderIdLavel2 = Math.abs(random.nextInt()) % COUNT_FOLDER_LEVEL_2;
        String imagePath = "";
        String baseImagePath = "";

        if(!"".equals(basePaht)){
            baseImagePath = baseImagePath.concat(basePaht.concat("/"));
        }
        imagePath = imagePath.concat(NAME_FOLDER_FILE);
        imagePath = imagePath.concat("/");
        imagePath = imagePath.concat(FOLDER_NAME_LEVEL_1.concat(String.valueOf(folderIdLavel1)));
        imagePath = imagePath.concat("/");
        imagePath = imagePath.concat(FOLDER_NAME_LEVEL_2.concat(String.valueOf(folderIdLavel2)));
        File dir = new File(baseImagePath.concat("/").concat(imagePath));
        dir.mkdirs();

        if(!dir.exists()){
            throw new Exception("Не удалось создать папку.");
        }

        return imagePath;
    }

    public static String getBaseImageFileName(String fileName, long size){
        String hashFileName = MD5.createMD5(fileName).substring(0,4);
        String hashSize = MD5.createMD5(String.valueOf(size)).substring(0,4);
        String time = String.valueOf(new Date().getTime()).substring(9);
        return hashFileName.concat(hashSize).concat(time);
    }

    public static String getImageFileName(String fileName, long size) throws NamingException {
        return getBaseImageFileName(fileName, size).concat(".").concat(IMAGE_TYPE);
    }

    public static String getImageFileName(String fileName, long size, int widthOrHeight) throws NamingException {
        return getBaseImageFileName(fileName, size).concat("_").concat(String.valueOf(widthOrHeight)).concat(".").concat(IMAGE_TYPE);
    }

    public static String getImageFileName(String fileName, long size, int width, int height) throws NamingException {
        return getBaseImageFileName(fileName, size).concat("_").concat(String.valueOf(width)).concat("x").concat(String.valueOf(height)).concat(".").concat(IMAGE_TYPE);
    }

    public static String getAllImageFileUrl(String basePath, String fileName, long size) throws Exception {
        return getImagePath(basePath).concat("/").concat(getImageFileName(fileName, size));
    }

    public static String getAllImageFileUrl(String basePath, String fileName, long size, int widthOrHeight) throws Exception {
        return getImagePath(basePath).concat("/").concat(getImageFileName(fileName, size, widthOrHeight));
    }
    public static String getAllImageFileUrl(String basePath, String fileName, long size, int width, int height) throws Exception {
        return getImagePath(basePath).concat("/").concat(getImageFileName(fileName, size, width, height));
    }
}
