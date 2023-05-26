/*
 * Copyright 2023 Kirill Lomakin
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

package things.graphics.awtgl;

import things.HangarState;
import things.graphics.HangarGraphicsProvider;
import things.graphics.HangarOffscreenBuffer;
import things.ui.components.wrappers.canvas.HangarCanvasWrapperLWJGL3;

public class HangarAWTGLOffscreenBuffer implements HangarOffscreenBuffer {
    private int width, height;
    private final HangarAWTGLGraphicsProvider graphicsProvider;

    public HangarAWTGLOffscreenBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.graphicsProvider = new HangarAWTGLGraphicsProvider();
    }

    @Override
    public HangarGraphicsProvider getGraphicsProvider() {
        return graphicsProvider;
    }

    @Override
    public void flushToCanvasWrapper(int x, int y, int width, int height) {
        var canvasWrapper = HangarState.getMainFrame().getViewport().getCanvasWrapper();
        if (canvasWrapper instanceof HangarCanvasWrapperLWJGL3 canvasWrapperLWJGL3) {
            canvasWrapperLWJGL3.getGraphicsProvider().paintOffscreenBuffer(this);
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void refreshResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }
}