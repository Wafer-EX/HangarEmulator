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

#version 330 core
layout (location = 0) in vec2 position;
layout (location = 1) in vec4 color;
layout (location = 2) in vec2 resolution;

out vec4 vertexColor;

void main() {
    mat4 ortho = mat4(2.0 / resolution.x, 0.0,                 0.0, -1.0,
                      0.0,                2.0 / -resolution.y, 0.0, 1.0,
                      0.0,                0.0,                 1.0, 0.0,
                      0.0,                0.0,                 0.0, 1.0);

    gl_Position = vec4(position.x, position.y, 0.0, 1.0) * ortho;
    vertexColor = color;
}