/*
 * Copyright 2022-2023 Kirill Lomakin
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

package things.implementations.nokia;

import com.nokia.mid.ui.DirectGraphics;
import things.utils.nokia.DirectGraphicsUtils;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DirectGraphicsImplementation implements DirectGraphics {
    private final Graphics meGraphics;

    public DirectGraphicsImplementation(Graphics graphics) {
        this.meGraphics = graphics;
    }

    @Override
    public void setARGBColor(int argbColor) {
        meGraphics.getSEGraphics().setColor(new Color(argbColor, true));
    }

    @Override
    public void drawImage(Image img, int x, int y, int anchor, int manipulation) throws IllegalArgumentException, NullPointerException {
        if (img == null) {
            throw new NullPointerException();
        }
        var image = new Image(DirectGraphicsUtils.manipulateImage(img.getSEImage(), manipulation), true);
        meGraphics.drawImage(image, x, y, anchor);
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
        var xPoints = new int[] { x1, x2, x3 };
        var yPoints = new int[] { y1, y2, y3 };
        meGraphics.getSEGraphics().setColor(new Color(argbColor, true));
        meGraphics.getSEGraphics().drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
        var xPoints = new int[] { x1, x2, x3 };
        var yPoints = new int[] { y1, y2, y3 };
        meGraphics.getSEGraphics().setColor(new Color(argbColor, true));
        meGraphics.getSEGraphics().fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (xPoints == null || yPoints == null) {
            throw new NullPointerException();
        }
        meGraphics.getSEGraphics().setColor(new Color(argbColor, true));
        meGraphics.getSEGraphics().drawPolygon(xPoints, yPoints, argbColor);
    }

    @Override
    public void fillPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (xPoints == null || yPoints == null) {
            throw new NullPointerException();
        }
        meGraphics.getSEGraphics().setColor(new Color(argbColor, true));
        meGraphics.getSEGraphics().fillPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void drawPixels(int[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (pixels == null) {
            throw new NullPointerException();
        }
        var image = new BufferedImage(width, height, DirectGraphicsUtils.getBufferedImageType(format));
        image.setRGB(0, 0, width, height, pixels, offset, scanlength);
        image = DirectGraphicsUtils.manipulateImage(image, manipulation);
        meGraphics.getSEGraphics().drawImage(image, x, y, null);
    }

    @Override
    public void drawPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void drawPixels(short[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void getPixels(int[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void getPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void getPixels(short[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public int getNativePixelFormat() {
        // TODO: write method logic
        return 0;
    }

    @Override
    public int getAlphaComponent() {
        return meGraphics.getSEGraphics().getColor().getAlpha();
    }
}