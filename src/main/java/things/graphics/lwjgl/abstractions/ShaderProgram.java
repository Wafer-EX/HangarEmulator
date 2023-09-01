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

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShaderProgram {
    private final int identifier;

    public ShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
        CharSequence vertexShaderSource = readShaderFile(vertexShaderPath);
        CharSequence fragmentShaderSource = readShaderFile(fragmentShaderPath);

        assert vertexShaderSource != null;
        assert fragmentShaderSource != null;

        int vertexShader = GL46.glCreateShader(GL46.GL_VERTEX_SHADER);
        GL46.glShaderSource(vertexShader, vertexShaderSource);
        GL46.glCompileShader(vertexShader);

        int fragmentShader = GL46.glCreateShader(GL46.GL_FRAGMENT_SHADER);
        GL46.glShaderSource(fragmentShader, fragmentShaderSource);
        GL46.glCompileShader(fragmentShader);

        identifier = GL46.glCreateProgram();
        GL46.glAttachShader(identifier, vertexShader);
        GL46.glAttachShader(identifier, fragmentShader);
        GL46.glLinkProgram(identifier);

        GL46.glDeleteShader(vertexShader);
        GL46.glDeleteShader(fragmentShader);
    }

    public int getIdentifier() {
        return identifier;
    }

    public void use() {
        GL46.glUseProgram(identifier);
    }

    public void setUniform(String name, int value) {
        int location = getLocation(name);
        GL46.glUniform1i(location, value);
    }

    public void setUniform(String name, float value) {
        int location = getLocation(name);
        GL46.glUniform1f(location, value);
    }

    public void setUniform(String name, float[] value) {
        int location = getLocation(name);
        GL46.glUniformMatrix4fv(location, false, value);
    }

    private CharSequence readShaderFile(String name) {
        // TODO: improve code quality?
        try {
            var resource = ShaderProgram.class.getResourceAsStream(name);
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
        int location = GL46.glGetUniformLocation(identifier, name);
        if (location == -1) {
            throw new IllegalStateException();
        }
        return location;
    }
}