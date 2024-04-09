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

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.MIDletResources;
import aq.waferex.hangaremulator.graphics.HangarImage;
import aq.waferex.hangaremulator.graphics.swing.HangarSwingImage;
import aq.waferex.hangaremulator.graphics.swing.HangarSwingGraphicsProvider;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// TODO: remove BufferedImage using
public class Image {
    private final HangarImage image;
    private final boolean isMutable;

    public Image(HangarImage image, boolean isMutable) {
        this.image = image;
        this.isMutable = isMutable;
    }

    public BufferedImage getSEImage() {
        if (image instanceof HangarSwingImage hangarSwingImage) {
            return hangarSwingImage.getBufferedImage();
        }
        throw new IllegalStateException();
    }

    public HangarImage getHangarImage() {
        return image;
    }

    public static Image createImage(int width, int height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        return new Image(HangarImage.create(width, height, Color.WHITE.getRGB(), false), true);
    }

    public static Image createImage(Image source) throws NullPointerException {
        if (source == null) {
            throw new NullPointerException();
        }
        if (source.isMutable()) {
            var bufferedImage = (BufferedImage) source.getSEImage();
            var colorModel = bufferedImage.getColorModel();
            var isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
            var writableRaster = bufferedImage.copyData(null);
            var bufferedImageClone = new BufferedImage(colorModel, writableRaster, isAlphaPremultiplied, null);
            return new Image(HangarImage.create(bufferedImageClone), false);
        }
        else {
            return source;
        }
    }

    public static Image createImage(String name) throws NullPointerException, IOException {
        if (name == null) {
            throw new NullPointerException();
        }
        var stream = MIDletResources.getResource(name);
        if (stream == null) {
            throw new IOException();
        }
        return new Image(HangarImage.create(stream), false);
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
        var imageRegion = image.getSEImage().getSubimage(x, y, width, height);
        var transformedImage = ImageUtils.transformImage(imageRegion, transform);
        return new Image(HangarImage.create(transformedImage), false);
    }

    public Graphics getGraphics() throws IllegalStateException {
        if (isMutable()) {
            var graphics = getSEImage().getGraphics();
            HangarState.applyAntiAliasing(graphics);
            return new Graphics(new HangarSwingGraphicsProvider(graphics));
        }
        else {
            throw new IllegalStateException();
        }
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public boolean isMutable() {
        return isMutable;
    }

    public static Image createImage(InputStream stream) throws NullPointerException, IOException {
        if (stream == null) {
            throw new NullPointerException();
        }
        var bufferedImage = ImageIO.read(stream);
        if (bufferedImage == null) {
            throw new IOException();
        }
        return new Image(HangarImage.create(bufferedImage), false);
    }

    public static Image createRGBImage(int[] rgb, int width, int height, boolean processAlpha) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (rgb == null) {
            throw new NullPointerException();
        }
        else if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        return new Image(HangarImage.create(rgb, width, height, processAlpha), false);
    }

    public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (rgbData == null) {
            throw new NullPointerException();
        }
        if (scanlength < width) {
            throw new IllegalArgumentException();
        }
        image.getRGB(x, y, width, height, rgbData, offset, scanlength);
    }
}