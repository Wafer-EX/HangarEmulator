/*
 * Copyright 2022 Kirill Lomakin
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

import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import things.HangarState;
import things.MIDletResources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Image {
    private final BufferedImage seImage;
    private final boolean isMutable;

    public Image(BufferedImage image, boolean isMutable) {
        this.seImage = image;
        this.isMutable = isMutable;
    }

    public BufferedImage getSEImage() {
        return seImage;
    }

    public static Image createImage(int width, int height) throws IllegalArgumentException {
        return new Image(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), true);
    }

    public static Image createImage(Image source) throws NullPointerException {
        if (source == null) {
            throw new NullPointerException();
        }
        else {
            if (source.isMutable()) {
                var bufferedImage = (BufferedImage) source.seImage;
                var colorModel = bufferedImage.getColorModel();
                var isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
                var writableRaster = bufferedImage.copyData(null);
                var bufferedImageClone = new BufferedImage(colorModel, writableRaster, isAlphaPremultiplied, null);
                return new Image(bufferedImageClone, false);
            }
            else {
                return source;
            }
        }
    }

    public static Image createImage(String name) throws NullPointerException, IOException {
        var stream = MIDletResources.getResourceFromJar(name);
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
        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static Image createImage(Image image, int x, int y, int width, int height, int transform) throws NotImplementedException {
        // TODO: write method logic
        throw new NotImplementedException("createImage");
    }

    public Graphics getGraphics() throws IllegalStateException {
        if (isMutable()) {
            var graphics = seImage.getGraphics();
            HangarState.applyRenderingHints(graphics);
            return new Graphics(graphics);
        }
        else {
            throw new IllegalStateException();
        }
    }

    public int getWidth() {
        return seImage.getWidth(null);
    }

    public int getHeight() {
        return seImage.getHeight(null);
    }

    public boolean isMutable() {
        return isMutable;
    }

    public static Image createImage(InputStream stream) throws NullPointerException, IOException {
        if (stream == null) {
            throw new NullPointerException();
        }
        try {
            var bufferedImage = ImageIO.read(stream);
            return new Image(bufferedImage, false);
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Image createRGBImage(int[] rgb, int width, int height, boolean processAlpha) throws NullPointerException, IllegalArgumentException, ArrayIndexOutOfBoundsException {
        var image = new BufferedImage(width, height, processAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, rgb, 0, width);
        return new Image(image, false);
    }

    public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        seImage.getRGB(x, y, width, height, rgbData, offset, scanlength);
    }
}