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

package things.ui.components.wrappers.canvas;

import org.lwjgl.opengl.awt.GLData;
import things.graphics.opengl.HangarGLGraphicsProvider;
import things.graphics.opengl.RenderTarget;
import things.ui.components.wrappers.canvas.lwjgl.HangarAWTGLCanvas;

import javax.microedition.lcdui.Canvas;
import javax.swing.*;
import java.awt.*;

public class HangarCanvasWrapperOpenGL extends HangarCanvasWrapper {
    private final HangarAWTGLCanvas glCanvas;
    private final HangarGLGraphicsProvider graphicsProvider;

    public HangarCanvasWrapperOpenGL(Canvas canvas) {
        super(canvas);
        
        this.glCanvas = new HangarAWTGLCanvas(new GLData());
        this.graphicsProvider = new HangarGLGraphicsProvider(RenderTarget.getDefault(240, 320));

        glCanvas.setFocusable(false);
        glCanvas.setPreferredSize(this.getPreferredSize());

        this.add(glCanvas);
        // TODO: react when resize
    }

    public HangarGLGraphicsProvider getGraphicsProvider() {
        return graphicsProvider;
    }

    @Override
    public Rectangle getDisplayedArea() {
        // TODO: write method logic
        return new Rectangle(0, 0, 240, 320);
    }

    @Override
    public double getScaleFactor() {
        // TODO: write method logic
        return 1.0;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        canvas.paint(new javax.microedition.lcdui.Graphics(graphicsProvider));
        glCanvas.setGLActionList(graphicsProvider.getGLActions());
        graphicsProvider.getGLActions().clear();
        SwingUtilities.invokeLater(glCanvas::render);
    }
}