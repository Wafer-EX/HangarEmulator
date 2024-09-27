/*
 * Copyright 2023-2024 Wafer EX
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
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import java.awt.image.BufferedImage;

public class HangarOffscreenBuffer {
    private BufferedImage additionalBuffer;
    private HangarGraphicsProvider graphicsProvider;

    public HangarOffscreenBuffer(int width, int height) {
        refreshResolution(width, height);
    }

    public BufferedImage getBufferedImage() {
        return additionalBuffer;
    }

    public HangarGraphicsProvider getGraphicsProvider() {
        return graphicsProvider;
    }

    public void flushToCanvasWrapper(int x, int y, int width, int height) {
        var canvasWrapper = HangarState.getMainFrame().getViewport().getCanvasWrapper();
        canvasWrapper.getBufferedImage().getGraphics().drawImage(additionalBuffer, x, y, width, height, null);
    }

    public int getWidth() {
        return additionalBuffer.getWidth();
    }

    public int getHeight() {
        return additionalBuffer.getHeight();
    }

    public void refreshResolution(int width, int height) {
        this.additionalBuffer = ImageUtils.createCompatibleImage(width, height);
        this.graphicsProvider = new HangarGraphicsProvider(additionalBuffer.getGraphics());
    }
}