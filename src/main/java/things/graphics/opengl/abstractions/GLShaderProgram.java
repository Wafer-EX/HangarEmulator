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

package things.graphics.opengl.abstractions;

import org.joml.Matrix4f;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL33.*;

// TODO: dispose
public class GLShaderProgram {
    private final int identifier;

    public GLShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
        CharSequence vertexShaderSource = readShaderFile(vertexShaderPath);
        CharSequence fragmentShaderSource = readShaderFile(fragmentShaderPath);

        assert vertexShaderSource != null;
        assert fragmentShaderSource != null;

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);

        identifier = glCreateProgram();
        glAttachShader(identifier, vertexShader);
        glAttachShader(identifier, fragmentShader);
        glLinkProgram(identifier);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public int getIdentifier() {
        return identifier;
    }

    public void use() {
        glUseProgram(identifier);
    }

    public void setUniform(String name, Matrix4f matrix) {
        int location = getLocation(name);
        float[] data = new float[16];
        glUniformMatrix4fv(location, false, matrix.get(data));
    }

    private CharSequence readShaderFile(String name) {
        // TODO: improve code quality?
        try {
            var resource = GLShaderProgram.class.getResourceAsStream(name);
            var bufferedReader = new BufferedReader(new InputStreamReader(resource));
            var stringBuilder = new StringBuilder();
            var line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private int getLocation(String name) {
        int location = glGetUniformLocation(identifier, name);
        if (location == -1) {
            throw new IllegalStateException();
        }
        return location;
    }
}