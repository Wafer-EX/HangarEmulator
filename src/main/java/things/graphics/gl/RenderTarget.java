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
    private boolean isInitialized;
    private final boolean isDefault;

    private static final RenderTarget defaultRenderTarget = new RenderTarget();

    public RenderTarget(int width, int height) {
        this.width = width;
        this.height = height;
        this.isDefault = false;
        this.isInitialized = false;
    }

    private RenderTarget() {
        this.width = 240;
        this.height = 320;
        this.isDefault = true;
        this.isInitialized = true;
    }

    public void initialize() {
        if (!isDefault) {
            glTexture = new GLTexture(width, height, GL_RGB, GL_RGB, GL_UNSIGNED_BYTE);
            glFramebuffer = new GLFramebuffer();
            glFramebuffer.attachTexture(glTexture, GL_COLOR_ATTACHMENT0);
            isInitialized = true;
        }
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public GLTexture getTexture() {
        if (isDefault) {
            throw new IllegalStateException();
        }
        return glTexture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void use() {
        if (isDefault) {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
        }
        else {
            glFramebuffer.bind();
        }
        glViewport(0, 0, width, height);
    }

    public static RenderTarget getDefault() {
        return defaultRenderTarget;
    }

    public static RenderTarget getDefault(int screenWidth, int screenHeight) {
        defaultRenderTarget.width = screenWidth;
        defaultRenderTarget.height = screenHeight;
        return defaultRenderTarget;
    }
}