package org.example;

import java.awt.image.BufferedImage;

public class Image {
    private String filename;
    private BufferedImage image;

    public Image(String filename, BufferedImage image) {
        this.filename = filename;
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public BufferedImage getImage() {
        return image;
    }
}
