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

package aq.waferex.hangaremulator.graphics;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.graphics.swing.HangarSwingImage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class HangarImage implements Cloneable {
    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize);

    public abstract HangarGraphicsProvider getGraphicsProvider();

    public static HangarImage create(int width, int height, int color, boolean hasAlpha) {
        return switch (HangarState.getGraphicsSettings().getGraphicsEngine()) {
            case Swing -> new HangarSwingImage(width, height, color, hasAlpha);
            case OpenGL -> throw new IllegalStateException();
        };
    }

    public static HangarImage create(InputStream stream) throws IOException {
        return switch (HangarState.getGraphicsSettings().getGraphicsEngine()) {
            case Swing -> new HangarSwingImage(stream);
            case OpenGL -> throw new IllegalStateException();
        };
    }

    public static HangarImage create(int[] rgb, int width, int height, boolean processAlpha) {
        return switch (HangarState.getGraphicsSettings().getGraphicsEngine()) {
            case Swing -> new HangarSwingImage(rgb, width, height, processAlpha);
            case OpenGL -> throw new IllegalStateException();
        };
    }

    public static HangarImage create(BufferedImage bufferedImage) {
        return switch (HangarState.getGraphicsSettings().getGraphicsEngine()) {
            case Swing -> new HangarSwingImage(bufferedImage);
            case OpenGL -> throw new IllegalStateException();
        };
    }

    @Override
    public abstract HangarImage clone();
}