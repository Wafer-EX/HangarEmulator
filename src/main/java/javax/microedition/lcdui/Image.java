package javax.microedition.lcdui;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import things.MIDletResources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Image {
    public final java.awt.Image image;

    private Image(java.awt.Image image) {
        this.image = image;
    }

    public static Image createImage(int width, int height) {
        return new Image(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    }

    public static Image createImage(Image source) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("createImage");
    }

    public static Image createImage(String name) throws IOException {
        var stream = MIDletResources.getResourceFromJar(name);
        return new Image(ImageIO.read(stream));
    }

    public static Image createImage(byte[] imageData, int imageOffset, int imageLength) throws NullPointerException {
        if (imageData == null) {
            throw new NullPointerException();
        }
        try {
            ByteArrayInputStream imageInputStream = new ByteArrayInputStream(imageData, imageOffset, imageLength);
            BufferedImage bufferedImage = ImageIO.read(imageInputStream);
            Image image = new Image(bufferedImage);
            return image;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Image createImage(Image image, int x, int y, int width, int height, int transform) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("createImage");
    }

    public Graphics getGraphics() throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("getGraphics");
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public boolean isMutable() {
        // TODO: it is correct?
        return true;
    }

    public static Image createImage(InputStream stream) throws IOException {
        if (stream == null) {
            throw new NullPointerException();
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(stream);
            Image image = new Image(bufferedImage);
            return image;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Image createRGBImage(int[] rgb, int width, int height, boolean processAlpha) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("createRGBImage");
    }

    public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("createRGBImage");
    }
}