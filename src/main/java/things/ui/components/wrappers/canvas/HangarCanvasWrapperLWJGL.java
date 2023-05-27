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

package things.ui.components.wrappers.canvas;

import org.lwjgl.opengl.awt.GLData;
import things.graphics.lwjgl.HangarLWJGLGraphicsProvider;
import things.ui.components.wrappers.canvas.lwjgl.HangarLWJGLCanvas;

import javax.microedition.lcdui.Canvas;
import java.awt.*;

public class HangarCanvasWrapperLWJGL extends HangarCanvasWrapper {
    private final GLData glData;
    private final HangarLWJGLCanvas lwjglCanvas;
    private final HangarLWJGLGraphicsProvider graphicsProvider;

    public HangarCanvasWrapperLWJGL(Canvas canvas) {
        super(canvas);

        this.glData = new GLData();
        this.lwjglCanvas = new HangarLWJGLCanvas(glData);
        this.graphicsProvider = new HangarLWJGLGraphicsProvider(0);

        lwjglCanvas.setFocusable(false);
        lwjglCanvas.setPreferredSize(this.getPreferredSize());

        this.add(lwjglCanvas);
    }

    public HangarLWJGLGraphicsProvider getGraphicsProvider() {
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

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        canvas.paint(new javax.microedition.lcdui.Graphics(graphicsProvider));
        lwjglCanvas.setLwjglActions(graphicsProvider.getGLActions());
        graphicsProvider.getGLActions().clear();
        lwjglCanvas.render();
    }
}