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

package aq.waferex.hangaremulator.graphics.opengl.abstractions;

import static org.lwjgl.opengl.GL33.*;

// TODO: dispose
public class GLBuffer {
    private final int target;
    private final int identifier;
    private int count;
    private int length;

    public GLBuffer(int target, float[] data) {
        this.target = target;

        identifier = glGenBuffers();
        setBufferData(data);
    }

    public void setBufferData(float[] data) {
        count = data != null ? data.length : 0;
        length = data != null ? data.length * 4 : 0;

        glBindBuffer(target, identifier);
        // TODO: That's wrong, change it
        glBufferData(target, data != null ? data : new float[1], GL_DYNAMIC_DRAW);
    }

    public void bind() {
        glBindBuffer(target, identifier);
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getCount() {
        return count;
    }

    public int getLength() {
        return length;
    }
}