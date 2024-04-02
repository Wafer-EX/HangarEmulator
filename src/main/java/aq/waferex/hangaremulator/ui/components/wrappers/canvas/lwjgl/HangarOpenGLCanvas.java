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

package aq.waferex.hangaremulator.ui.components.wrappers.canvas.lwjgl;

import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;
import aq.waferex.hangaremulator.graphics.opengl.HangarGLAction;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL33.*;

public class HangarOpenGLCanvas extends AWTGLCanvas {
    private final ArrayList<HangarGLAction> glActionList;
    private final Dimension viewportResolution = new Dimension(240, 320);

    public HangarOpenGLCanvas(GLData glData) {
        super(glData);
        this.glActionList = new ArrayList<>();
    }

    public void setGLActionList(ArrayList<HangarGLAction> glActions) {
        this.glActionList.addAll(glActions);
    }

    public void setViewportResolution(int width, int height) {
        viewportResolution.width = width;
        viewportResolution.height = height;
    }

    @Override
    public void initGL() {
        createCapabilities();
        glViewport(0, 0, viewportResolution.width, viewportResolution.height);
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glEnable(GL_SCISSOR_TEST);
        glScissor(0, 0, viewportResolution.width, viewportResolution.height);
    }

    @Override
    public void paintGL() {
        glClear(GL_COLOR_BUFFER_BIT);
        for (var glAction : glActionList) {
            glAction.execute();
        }
        glActionList.clear();
        glScissor(0, 0, viewportResolution.width, viewportResolution.height);
        swapBuffers();
    }
}