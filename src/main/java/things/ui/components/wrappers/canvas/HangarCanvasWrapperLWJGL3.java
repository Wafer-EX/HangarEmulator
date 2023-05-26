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
import things.graphics.awtgl.HangarAWTGLCanvas;
import things.graphics.awtgl.HangarAWTGLGraphicsProvider;

import javax.microedition.lcdui.Canvas;
import java.awt.*;

public class HangarCanvasWrapperLWJGL3 extends HangarCanvasWrapper {
    private GLData glData;
    private HangarAWTGLCanvas awtglCanvas;
    private HangarAWTGLGraphicsProvider graphicsProvider;

    public HangarCanvasWrapperLWJGL3(Canvas canvas) {
        super(canvas);

        this.glData = new GLData();
        this.awtglCanvas = new HangarAWTGLCanvas(glData);
        this.graphicsProvider = new HangarAWTGLGraphicsProvider(awtglCanvas);

        this.add(awtglCanvas);
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
        awtglCanvas.render();
    }
}