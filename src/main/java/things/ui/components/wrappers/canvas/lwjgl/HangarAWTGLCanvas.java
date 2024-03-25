/*
 * Copyright 2023-2024 Kirill Lomakin
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

package things.ui.components.wrappers.canvas.lwjgl;

import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;
import things.graphics.gl.HangarGLAction;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

public class HangarAWTGLCanvas extends AWTGLCanvas {
    private final ArrayList<HangarGLAction> glActionList;

    public HangarAWTGLCanvas(GLData glData) {
        super(glData);
        this.glActionList = new ArrayList<>();
    }

    public void setGLActionList(ArrayList<HangarGLAction> glActions) {
        this.glActionList.addAll(glActions);
    }

    @Override
    public void initGL() {
        createCapabilities();
        glViewport(0, 0, 240, 320);
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void paintGL() {
        glClear(GL_COLOR_BUFFER_BIT);
        for (var glAction : glActionList) {
            glAction.execute();
        }
        glActionList.clear();
        swapBuffers();
    }
}