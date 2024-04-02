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

package aq.waferex.hangaremulator.ui.components.wrappers.canvas;

import aq.waferex.hangaremulator.HangarState;
import org.lwjgl.opengl.awt.GLData;
import aq.waferex.hangaremulator.graphics.opengl.HangarGLGraphicsProvider;
import aq.waferex.hangaremulator.graphics.opengl.RenderTarget;
import aq.waferex.hangaremulator.ui.components.wrappers.canvas.lwjgl.HangarOpenGLCanvas;

import javax.microedition.lcdui.Canvas;
import javax.swing.*;
import java.awt.*;

public class HangarCanvasWrapperOpenGL extends HangarCanvasWrapper {
    private final HangarOpenGLCanvas openGLCanvas;
    private final HangarGLGraphicsProvider graphicsProvider;

    public HangarCanvasWrapperOpenGL(Canvas canvas) {
        super(canvas);

        var graphicsSettings = HangarState.getGraphicsSettings();
        var resolution = graphicsSettings.getResolution();

        this.graphicsProvider = new HangarGLGraphicsProvider(RenderTarget.getDefault(resolution.width, resolution.height));
        this.openGLCanvas = new HangarOpenGLCanvas(new GLData());

        openGLCanvas.setViewportResolution(bufferScale.width, bufferScale.height);
        openGLCanvas.setFocusable(false);
        openGLCanvas.setPreferredSize(this.getPreferredSize());

        this.add(openGLCanvas);
        // TODO: react when resize
    }

    public HangarGLGraphicsProvider getGraphicsProvider() {
        return graphicsProvider;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        canvas.paint(new javax.microedition.lcdui.Graphics(graphicsProvider));
        openGLCanvas.setGLActionList(graphicsProvider.getGLActions());
        graphicsProvider.getGLActions().clear();
        SwingUtilities.invokeLater(openGLCanvas::render);
    }
}