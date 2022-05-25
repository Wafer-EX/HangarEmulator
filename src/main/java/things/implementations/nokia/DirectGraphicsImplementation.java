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

package things.implementations.nokia;

import com.nokia.mid.ui.DirectGraphics;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class DirectGraphicsImplementation implements DirectGraphics {
    private Graphics meGraphics;

    public DirectGraphicsImplementation(Graphics graphics) {
        this.meGraphics = graphics;
    }

    @Override
    public void setARGBColor(int argbColor) {

    }

    @Override
    public void drawImage(Image img, int x, int y, int anchor, int manipulation) {
        meGraphics.drawImage(img, x, y, anchor);
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
        var xPoints = new int[] { x1, x2, x3 };
        var yPoints = new int[] { y1, y2, y3 };
        meGraphics.seGraphics.drawPolygon(xPoints, yPoints, argbColor);
    }

    @Override
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
        var xPoints = new int[] { x1, x2, x3 };
        var yPoints = new int[] { y1, y2, y3 };
        meGraphics.seGraphics.fillPolygon(xPoints, yPoints, argbColor);
    }

    @Override
    public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) {

    }

    @Override
    public void fillPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) {

    }

    @Override
    public void drawPixels(int[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) {

    }

    @Override
    public void drawPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) {

    }

    @Override
    public void drawPixels(short[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) {

    }

    @Override
    public void getPixels(int[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) {

    }

    @Override
    public void getPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int format) {

    }

    @Override
    public void getPixels(short[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) {

    }

    @Override
    public int getNativePixelFormat() {
        return 0;
    }

    @Override
    public int getAlphaComponent() {
        return 0;
    }
}