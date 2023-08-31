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

package things.graphics.lwjgl.abstractions;

import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;

// TODO: check this and use
public class BufferObject {
    private int target;
    private int identifier;

    public BufferObject(int target, FloatBuffer buffer) {
        this.target = target;

        identifier = GL46.glGenBuffers();
        setBufferData(buffer);
    }

    public void setBufferData(FloatBuffer buffer) {
        GL46.glBindBuffer(target, identifier);
        GL46.glBufferData(target, buffer, GL46.GL_DYNAMIC_DRAW);
    }

    public void bind() {
        GL46.glBindBuffer(target, identifier);
    }

    public int getTarget() {
        return target;
    }

    public int getIdentifier() {
        return identifier;
    }
}