package com.godpalace.fastlibrary.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ImageConversion {
    public static byte[] imageToBytes(BufferedImage image, ImageType imageType) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, imageType.toString(), out);

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static BufferedImage bytesToImage(byte[] bytes) {
        try {
            return ImageIO.read(ImageIO.createImageInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static BufferedImage toBufferedImage(ImageIcon icon) {
        return toBufferedImage(icon.getImage());
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bimage = new BufferedImage(
                image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}
