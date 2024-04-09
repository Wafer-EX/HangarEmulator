package aq.waferex.hangaremulator.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class HangarSwingImage extends HangarImage {
    private final BufferedImage bufferedImage;

    public HangarSwingImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public HangarSwingImage(int width, int height, int color, boolean hasAlpha) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        var graphics = bufferedImage.createGraphics();
        graphics.setColor(new Color(color, hasAlpha));
        graphics.fillRect(0, 0, width, height);
    }

    public HangarSwingImage(InputStream stream) throws IOException {
        bufferedImage = ImageIO.read(stream);
    }

    public HangarSwingImage(int[] rgb, int width, int height, boolean processAlpha) {
        bufferedImage = new BufferedImage(width, height, processAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, width, height, rgb, 0, width);
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public void getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
        bufferedImage.getRGB(startX, startY, w, h, rgbArray, offset, scansize);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}