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
import aq.waferex.hangaremulator.graphics.HangarImage;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Image {
    private final HangarImage image;
    private final boolean isMutable;
    private Graphics graphics;

    public Image(HangarImage image, boolean isMutable) {
        this.image = image;
        this.isMutable = isMutable;
        // TODO: replace with normal provider
        // this.graphics = new Graphics(image.getGraphicsProvider());
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
        return source.isMutable ? new Image(source.getHangarImage().getCopy(), false) : source;
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
        return new Image(image.getHangarImage().getCopy(x, y, width, height, transform), false);
    }

    public Graphics getGraphics() throws IllegalStateException {
        if (!isMutable) {
            throw new IllegalStateException();
        }
        return graphics;
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
        return new Image(HangarImage.create(stream), false);
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