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

package aq.waferex.hangaremulator.graphics.opengl;

import aq.waferex.hangaremulator.graphics.opengl.abstractions.GLFramebuffer;
import aq.waferex.hangaremulator.graphics.opengl.abstractions.GLTexture;

import static org.lwjgl.opengl.GL33.*;

// TODO: dispose
public class RenderTarget {
    private GLFramebuffer glFramebuffer;
    private GLTexture glTexture;
    private final int width;
    private final int height;
    private boolean isInitialized;

    public RenderTarget(int width, int height) {
        this.width = width;
        this.height = height;
        this.isInitialized = false;
    }

    public void initialize() {
        glTexture = new GLTexture(width, height, GL_RGB, GL_RGB, GL_UNSIGNED_BYTE);
        glTexture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glFramebuffer = new GLFramebuffer();
        glFramebuffer.attachTexture(glTexture, GL_COLOR_ATTACHMENT0);
        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public GLTexture getTexture() {
        return glTexture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void use() {
        glFramebuffer.bind();
        glViewport(0, 0, width, height);
    }
}