/*
 * Copyright 2024 Wafer EX
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

import things.graphics.gl.abstractions.GLFramebuffer;
import things.graphics.gl.abstractions.GLTexture;

import static org.lwjgl.opengl.GL33.*;

// TODO: dispose
public class RenderTarget {
    private final GLFramebuffer glFramebuffer;
    private final GLTexture glTexture;
    private final int width, height;

    public RenderTarget(int width, int height) {
        this.width = width;
        this.height = height;

        glFramebuffer = new GLFramebuffer();
        glTexture = new GLTexture(width, height, GL_RGB, GL_RGB, GL_UNSIGNED_BYTE);

        glFramebuffer.attachTexture(glTexture, GL_COLOR_ATTACHMENT0);
    }

    public GLTexture getTexture() {
        return glTexture;
    }

    public void use() {
        glFramebuffer.bind();
        glViewport(0, 0, width, height);
    }

    public static void bindDefault(int screenWidth, int screenHeight) {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, screenWidth, screenHeight);
    }
}