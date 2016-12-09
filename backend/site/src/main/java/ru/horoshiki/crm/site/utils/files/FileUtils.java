/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.horoshiki.crm.site.utils.files;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author goofy
 */
public class FileUtils {
    public static final String IMAGE_TYPE = "jpg";

    public FileUtils() {

    }

    public BufferedImage open(String filePath) throws IOException {
        return ImageIO.read(new File(filePath));
    }

    public void save(BufferedImage image, String filePath) throws IOException, NamingException {
        File file = new File(filePath);
        ImageIO.write(image, IMAGE_TYPE, file);
    }

    public BufferedImage widthResize(BufferedImage originalImage, int width) throws IOException {
        int height = originalImage.getHeight() * width / originalImage.getWidth();
        return resizeImage(originalImage, width, height);
    }

    public BufferedImage heightResize(BufferedImage originalImage, int height) throws IOException {
        int width = originalImage.getWidth() * height / originalImage.getHeight();
        return resizeImage(originalImage, width, height);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws IOException {
        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        Image image = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, null, null);
        return scaledImage;
    }

    public BufferedImage cropImage(BufferedImage originalImage, int x1, int y1, int x2, int y2) {
        BufferedImage scaledImage = new BufferedImage((x2 - x1), (y2 - y1), originalImage.getType());
        Image image = originalImage.getScaledInstance(originalImage.getWidth(), originalImage.getHeight(), Image.SCALE_SMOOTH);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, 0, 0, (x2 - x1), (y2 - y1), x1, y1, x2, y2, null);
        return scaledImage;
    }

    public BufferedImage watermarkImage(BufferedImage originalImage, BufferedImage watermark) {
        BufferedImage scaledImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
        Image image = originalImage.getScaledInstance(originalImage.getWidth(), originalImage.getHeight(), Image.SCALE_SMOOTH);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, null, null);
        g.drawImage(watermark, null, originalImage.getWidth()-(watermark.getWidth()+10), originalImage.getHeight()-(watermark.getHeight()+5));
        return scaledImage;
    }

    public static Boolean deleteFile(String path){
        File image = new File(path);
        return image.delete();
    }
}
