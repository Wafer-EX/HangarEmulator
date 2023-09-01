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

public class VertexArrayObject {
    private final int identifier;

    public VertexArrayObject() {
        identifier = GL46.glGenVertexArrays();
        Bind();
    }

    public void VertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int pointer) {
        GL46.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
        GL46.glEnableVertexAttribArray(index);
    }

    public void Bind() {
        GL46.glBindVertexArray(identifier);
    }
}