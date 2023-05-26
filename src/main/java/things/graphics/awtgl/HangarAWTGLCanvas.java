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

import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;
import things.HangarState;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

public class HangarAWTGLCanvas extends AWTGLCanvas {
    private final ArrayList<HangarGLAction> glActions;

    public HangarAWTGLCanvas(GLData glData) {
        super(glData);
        glActions = new ArrayList<>();
    }

    public void setGLActions(ArrayList<HangarGLAction> glActions) {
        this.glActions.addAll(glActions);
    }

    @Override
    public void initGL() {
        createCapabilities();
        glClearColor(0.5f, 0.5f, 0.5f, 1f);
    }

    @Override
    public void paintGL() {
        var profile = HangarState.getProfileManager().getCurrentProfile();
        int width = profile.getResolution().width;
        int height = profile.getResolution().height;

        glClear(GL_COLOR_BUFFER_BIT);
        // TODO: separate it for offscreen buffer
        glViewport(0, 0, width, height);

        glMatrixMode(GL_PROJECTION_MATRIX);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW_MATRIX);

        for (var glAction : glActions) {
            glAction.execute();
        }
        glActions.clear();
        swapBuffers();
    }
}