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

package things.graphics.gl.abstractions;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

// TODO: check this and use
// TODO: dispose
public class GLTexture {
    private int identifier;
    private int width;
    private int height;

    public GLTexture(int width, int height, int internalFormat, int pixelFormat, int pixelType) {
        this.width = width;
        this.height = height;
        this.identifier = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, identifier);
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, pixelFormat, pixelType, (ByteBuffer) null);
    }

    public GLTexture(ByteBuffer byteBuffer, int width, int height) {
        this.width = width;
        this.height = height;
        this.identifier = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, identifier);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    public void setParameter(int name, int value) {
        // TODO: check
        glBindTexture(GL_TEXTURE_2D, identifier);
        glTexParameteri(GL_TEXTURE_2D, name, value);
    }

    public void bind(int unit) {
        glActiveTexture(unit);
        glBindTexture(GL_TEXTURE_2D, identifier);
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}