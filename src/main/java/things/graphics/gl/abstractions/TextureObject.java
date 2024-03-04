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

import static org.lwjgl.opengl.GL46.*;

// TODO: check this and use
public class TextureObject {
    private int identifier;
    private int width;
    private int height;

    public TextureObject(int width, int height, int internalFormat, int pixelFormat, int pixelType) {
        this.width = width;
        this.height = height;
        this.identifier = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, identifier);
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, pixelFormat, pixelType, (ByteBuffer) null);
    }

    public TextureObject(String path) {
        // TODO: write method logic
    }

    public void setParameter(int name, int value) {
        glTextureParameteri(identifier, name, value);
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