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
    private GLFramebuffer glFramebuffer;
    private GLTexture glTexture;
    private int width, height;
    private final boolean isDefault;

    private static final RenderTarget defaultRenderTarget = new RenderTarget();

    public RenderTarget(int width, int height) {
        this.width = width;
        this.height = height;
        this.isDefault = false;
    }

    public RenderTarget() {
        this.width = 240;
        this.height = 320;
        this.isDefault = true;
    }

    public void initialize() {
        if (!isDefault) {
            glFramebuffer = new GLFramebuffer();
            glTexture = new GLTexture(width, height, GL_RGB, GL_RGB, GL_UNSIGNED_BYTE);
            glFramebuffer.attachTexture(glTexture, GL_COLOR_ATTACHMENT0);
        }
    }

    public GLTexture getTexture() {
        if (glTexture == null) {
            throw new IllegalStateException();
        }
        return glTexture;
    }

    public void use() {
        if (isDefault) {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
        }
        else {
            if (glFramebuffer == null) {
                throw new IllegalStateException();
            }
            glFramebuffer.bind();
        }
        glViewport(0, 0, width, height);
    }

    public static RenderTarget getDefault(int screenWidth, int screenHeight) {
        defaultRenderTarget.width = screenWidth;
        defaultRenderTarget.height = screenHeight;
        return defaultRenderTarget;
    }
}