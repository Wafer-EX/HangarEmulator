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

package aq.waferex.hangaremulator.graphics.swing;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.graphics.HangarGraphicsProvider;
import aq.waferex.hangaremulator.graphics.HangarOffscreenBuffer;
import aq.waferex.hangaremulator.ui.components.wrappers.canvas.HangarCanvasWrapperSwing;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;

import java.awt.image.BufferedImage;

public class HangarSwingOffscreenBuffer implements HangarOffscreenBuffer {
    private BufferedImage additionalBuffer;
    private HangarSwingGraphicsProvider graphicsProvider;

    public HangarSwingOffscreenBuffer(int width, int height) {
        refreshResolution(width, height);
    }

    public BufferedImage getBufferedImage() {
        return additionalBuffer;
    }

    @Override
    public HangarGraphicsProvider getGraphicsProvider() {
        return graphicsProvider;
    }

    @Override
    public void flushToCanvasWrapper(int x, int y, int width, int height) {
        var canvasWrapper = HangarState.getMainFrame().getViewport().getCanvasWrapper();
        if (canvasWrapper instanceof HangarCanvasWrapperSwing canvasWrapperSwing) {
            canvasWrapperSwing.getBuffer().getGraphics().drawImage(additionalBuffer, x, y, width, height, null);
        }
    }

    @Override
    public int getWidth() {
        return additionalBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return additionalBuffer.getHeight();
    }

    @Override
    public void refreshResolution(int width, int height) {
        this.additionalBuffer = ImageUtils.createCompatibleImage(width, height);
        this.graphicsProvider = new HangarSwingGraphicsProvider(additionalBuffer.getGraphics());
    }
}