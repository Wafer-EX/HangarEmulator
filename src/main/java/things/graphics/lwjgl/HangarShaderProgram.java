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

package things.graphics.lwjgl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;

public class HangarShaderProgram {
    private final CharSequence vertexShaderSource, fragmentShaderSource;
    private boolean isCompiled;
    private int shaderProgram;

    public HangarShaderProgram(String name) {
        this.vertexShaderSource = readShaderFile("/shaders/" + name + ".vert");
        this.fragmentShaderSource = readShaderFile("/shaders/" + name + ".frag");
    }

    private CharSequence readShaderFile(String name) {
        // TODO: improve code quality?
        try {
            var resource = HangarShaderProgram.class.getResourceAsStream(name);
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

            shaderProgram = glCreateProgram();
            glAttachShader(shaderProgram, vertexShader);
            glAttachShader(shaderProgram, fragmentShader);
            glLinkProgram(shaderProgram);

            glDeleteShader(vertexShader);
            glDeleteShader(fragmentShader);

            isCompiled = true;
            return shaderProgram;
        }
        else {
            throw new IllegalStateException();
        }
    }

    public int getShaderProgram() {
        if (!isCompiled) {
            throw new IllegalStateException();
        }
        return shaderProgram;
    }
}