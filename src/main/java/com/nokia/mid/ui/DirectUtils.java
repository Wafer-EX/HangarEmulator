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

package com.nokia.mid.ui;

import javax.imageio.ImageIO;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class DirectUtils {
    public static DirectGraphics getDirectGraphics(Graphics g) {
        //return new DirectGraphicsImplementation(g);
        return g.getGraphicsProvider().getDirectGraphics(g);
    }

    public static Image createImage(byte[] imageData, int imageOffset, int imageLength) throws ArrayIndexOutOfBoundsException, NullPointerException, IllegalArgumentException {
        if (imageData == null) {
            throw new NullPointerException();
        }
        try {
            var byteArrayInputStream = new ByteArrayInputStream(imageData, imageOffset, imageLength);
            var image = ImageIO.read(byteArrayInputStream);
            return new Image(image, true);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException();
        }
    }

    public static Image createImage(int width, int height, int ARGBcolor) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        var graphics2d = (Graphics2D) image.getGraphics();
        graphics2d.setColor(new Color(ARGBcolor, true));
        graphics2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        return new Image(image, true);
    }
}