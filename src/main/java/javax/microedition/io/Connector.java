/*
 * Copyright 2024 Wafer EX
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

package javax.microedition.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connector {
    public static final int READ = 1;
    public static final int WRITE = 2;
    public static final int READ_WRITE = 3;

    public static Connection open(String name) throws IOException {
        // TODO: write method logic
        throw  new ConnectionNotFoundException();
    }

    public static Connection open(String name, int mode) throws IOException {
        // TODO: write method logic
        throw  new ConnectionNotFoundException();
    }

    public static Connection open(String name, int mode, boolean timeouts) throws IOException {
        // TODO: write method logic
        throw  new ConnectionNotFoundException();
    }

    public static DataInputStream openDataInputStream(String name) throws IOException {
        // TODO: write method logic
        throw  new ConnectionNotFoundException();
    }

    public static InputStream openInputStream(String name) throws IOException {
        // TODO: write method logic
        throw  new ConnectionNotFoundException();
    }

    public static OutputStream openOutputStream(String name) throws IOException {
        // TODO: write method logic
        throw  new ConnectionNotFoundException();
    }
}