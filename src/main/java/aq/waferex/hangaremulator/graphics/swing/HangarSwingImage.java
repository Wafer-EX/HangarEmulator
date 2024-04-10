/*
 * Copyright 2024 Wafer EX
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

package aq.waferex.hangaremulator.graphics.swing;

import aq.waferex.hangaremulator.graphics.HangarGraphicsProvider;
import aq.waferex.hangaremulator.graphics.HangarImage;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;
import aq.waferex.hangaremulator.utils.nokia.DirectGraphicsUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

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

    @Override
    public HangarGraphicsProvider getGraphicsProvider() {
        // TODO: cache it?
        return new HangarSwingGraphicsProvider(bufferedImage.getGraphics());
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    // TODO: remove it
    public ByteBuffer convertToByteBuffer() {
        int[] pixels = bufferedImage.getRGB(0, 0, bufferedImage.getData().getWidth(), bufferedImage.getData().getHeight(), null, 0, bufferedImage.getData().getWidth());
        ByteBuffer buffer = ByteBuffer.allocateDirect(pixels.length * 4);
        for (int pixel : pixels) {
            buffer.put((byte) ((pixel >> 16) & 0xFF));
            buffer.put((byte) ((pixel >> 8) & 0xFF));
            buffer.put((byte) (pixel & 0xFF));
            buffer.put((byte) ((pixel >> 24) & 0xFF));
        }
        buffer.flip();
        return buffer;
    }

    @Override
    public HangarImage getCopy() {
        var colorModel = bufferedImage.getColorModel();
        var isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        var writableRaster = bufferedImage.copyData(null);
        var bufferedImageClone = new BufferedImage(colorModel, writableRaster, isAlphaPremultiplied, null);
        return new HangarSwingImage(bufferedImageClone);
    }

    @Override
    public HangarImage getCopy(int x, int y, int width, int height, int transform) {
        var imageRegion = bufferedImage.getSubimage(x, y, width, height);
        var transformedImage = ImageUtils.transformImage(imageRegion, transform);
        return new HangarSwingImage(transformedImage);
    }

    @Override
    public HangarImage getCopy(int manipulation) {
        var manipulatedImage = DirectGraphicsUtils.manipulateImage(bufferedImage, manipulation);
        return new HangarSwingImage(manipulatedImage);
    }
}