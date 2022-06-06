package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    TextColorSchema schema;
    public BufferedImage img;

    public Converter() {
        schema = new Schema("W@M%$SN8SI*~=.-'");
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        //Read the image from net
        img = ImageIO.read(new URL(url));

        //Check if fields are already set
        maxWidth = (maxWidth == 0) ? img.getWidth() : maxWidth;
        maxHeight = (maxHeight == 0) ? img.getHeight() : maxHeight;
        maxRatio = (maxRatio == 0) ? 1.0 * img.getWidth() / img.getHeight() : maxRatio;
//        ImageIO.write(img, "png", new File("outFirst.png")); //Remove after test
//        System.out.format("width: %d, height: %d\n\n", img.getWidth(), img.getHeight()); //Remove after test

        //Check ratio
        double ratio = 1.0 * img.getWidth() / img.getHeight();
        if (ratio > maxRatio || ratio < 1 / maxRatio)
            throw new BadImageSizeException(ratio, maxRatio);

        //Count new size
        double scaleValue = Math.min(1.0, Math.min(
                1.0 * maxWidth / img.getWidth(),
                1.0 * maxHeight / img.getHeight()));
        int newWidth = (int) (img.getWidth() * scaleValue);
        int newHeight = (int) (img.getHeight() * scaleValue);
//        System.out.format("newWidth: %d, newHeight: %d\n\n", newWidth, newHeight); //Remove after test

        //Scale and prepare image
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
//        ImageIO.write(bwImg, "png", new File("outBw.png")); //Remove after test

        //Build the string
        StringBuilder sb = new StringBuilder();
        int[] colorArray = new int[3];
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, colorArray)[0];
                sb.append(schema.convert(color));
                sb.append(schema.convert(color));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}