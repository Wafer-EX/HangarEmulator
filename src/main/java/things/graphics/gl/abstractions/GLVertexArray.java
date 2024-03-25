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

import static org.lwjgl.opengl.GL33.*;

// TODO: dispose
public class GLVertexArray {
    private final int identifier;

    public GLVertexArray() {
        identifier = glGenVertexArrays();
        bind();
    }

    public void VertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int pointer) {
        glVertexAttribPointer(index, size, type, normalized, stride, pointer);
        glEnableVertexAttribArray(index);
    }

    public void bind() {
        glBindVertexArray(identifier);
    }
}