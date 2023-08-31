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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;

public class ShaderProgram {
    private final CharSequence vertexShaderSource, fragmentShaderSource;
    private boolean isCompiled;
    private int identifier;

    public ShaderProgram(String name) {
        this.vertexShaderSource = readShaderFile("/shaders/" + name + ".vert");
        this.fragmentShaderSource = readShaderFile("/shaders/" + name + ".frag");
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

    public int compileShader() {
        if (!isCompiled) {
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

            isCompiled = true;
            return identifier;
        }
        else {
            throw new IllegalStateException();
        }
    }

    public int getIdentifier() {
        if (!isCompiled) {
            throw new IllegalStateException();
        }
        return identifier;
    }
}