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

package things.graphics.gl;

import things.HangarState;
import things.graphics.HangarGraphicsProvider;
import things.graphics.HangarOffscreenBuffer;
import things.ui.components.wrappers.canvas.HangarCanvasWrapperGL;

public class HangarGLOffscreenBuffer implements HangarOffscreenBuffer {
    private final HangarGLGraphicsProvider graphicsProvider;
    private final RenderTarget renderTarget;

    public HangarGLOffscreenBuffer(int width, int height) {
        this.renderTarget = new RenderTarget(width, height);
        this.graphicsProvider = new HangarGLGraphicsProvider(renderTarget);
    }

    @Override
    public HangarGraphicsProvider getGraphicsProvider() {
        return graphicsProvider;
    }

    @Override
    public void flushToCanvasWrapper(int x, int y, int width, int height) {
        var canvasWrapper = HangarState.getMainFrame().getViewport().getCanvasWrapper();
        if (canvasWrapper instanceof HangarCanvasWrapperGL canvasWrapperLWJGL) {
            canvasWrapperLWJGL.getGraphicsProvider().paintOffscreenBuffer(this);
        }
    }

    @Override
    public int getWidth() {
        return renderTarget.getWidth();
    }

    @Override
    public int getHeight() {
        return renderTarget.getHeight();
    }

    @Override
    public void refreshResolution(int width, int height) {
        // TODO: replace with new render target
    }
}