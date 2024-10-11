/*
 * Copyright 2022-2024 Wafer EX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.microedition.lcdui;

import aq.waferex.hangaremulator.MIDletResources;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Image {
    private final BufferedImage bufferedImage;
    private final Graphics2D graphics2D;
    private final Graphics meGraphics;
    private final boolean isMutable;

    public Image(BufferedImage image, boolean isMutable) {
        this.bufferedImage = image;
        this.graphics2D = (Graphics2D) bufferedImage.getGraphics();
        this.meGraphics = new Graphics(graphics2D);
        this.isMutable = isMutable;
    }

    public Image(int width, int height, int color, boolean hasAlpha) {
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(new Color(color, hasAlpha));
        graphics2D.fillRect(0, 0, width, height);
        this.meGraphics = new Graphics(graphics2D);
        this.isMutable = true;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public static Image createImage(int width, int height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        return new Image(width, height, Color.WHITE.getRGB(), false);
    }

    public static Image createImage(Image source) throws NullPointerException {
        if (source == null) {
            throw new NullPointerException();
        }

        var colorModel = source.bufferedImage.getColorModel();
        var isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        var writableRaster = source.bufferedImage.copyData(null);
        var bufferedImageClone = new BufferedImage(colorModel, writableRaster, isAlphaPremultiplied, null);

        return source.isMutable ? new Image(bufferedImageClone, false) : source;
    }

    public static Image createImage(String name) throws NullPointerException, IOException {
        if (name == null) {
            throw new NullPointerException();
        }
        var stream = MIDletResources.getResource(name);
        if (stream == null) {
            // TODO: this method doesn't work with Mini Game Pack
            throw new IOException();
        }
        return new Image(ImageIO.read(stream), false);
    }

    public static Image createImage(byte[] imageData, int imageOffset, int imageLength) throws ArrayIndexOutOfBoundsException, NullPointerException, IllegalArgumentException {
        if (imageData == null) {
            throw new NullPointerException();
        }
        try {
            var imageInputStream = new ByteArrayInputStream(imageData, imageOffset, imageLength);
            return createImage(imageInputStream);
        }
        catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    public static Image createImage(Image image, int x, int y, int width, int height, int transform) throws NullPointerException, IllegalArgumentException {
        if (image == null) {
            throw new NullPointerException();
        }
        var imageRegion = image.getBufferedImage().getSubimage(x, y, width, height);
        var transformedImage = ImageUtils.transformImage(imageRegion, transform);

        return new Image(transformedImage, false);
    }

    public Graphics getGraphics() throws IllegalStateException {
        if (!isMutable) {
            throw new IllegalStateException();
        }
        return meGraphics;
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    public boolean isMutable() {
        return isMutable;
    }

    public static Image createImage(InputStream stream) throws NullPointerException, IOException {
        if (stream == null) {
            throw new NullPointerException();
        }
        return new Image(ImageIO.read(stream), false);
    }

    public static Image createRGBImage(int[] rgb, int width, int height, boolean processAlpha) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (rgb == null) {
            throw new NullPointerException();
        }
        else if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }

        var bufferedImage = new BufferedImage(width, height, processAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, width, height, rgb, 0, width);

        return new Image(bufferedImage, false);
    }

    public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (rgbData == null) {
            throw new NullPointerException();
        }
        if (scanlength < width) {
            throw new IllegalArgumentException();
        }
        bufferedImage.getRGB(x, y, width, height, rgbData, offset, scanlength);
    }
}