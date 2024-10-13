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

package javax.microedition.io.file;

import java.io.*;
import java.util.Enumeration;

public interface FileConnection {
    public boolean isOpen();

    public InputStream openInputStream() throws IOException;

    public DataInputStream openDataInputStream() throws IOException;

    public OutputStream openOutputStream() throws IOException;

    public DataOutputStream openDataOutputStream() throws IOException;

    public OutputStream openOutputStream(long byteOffset) throws IOException;

    public long totalSize();

    public long availableSize();

    public long usedSize();

    public long directorySize(boolean includeSubDirs) throws IOException;

    public long fileSize() throws IOException;

    public boolean canRead();

    public boolean canWrite();

    public boolean isHidden();

    public void setReadable(boolean readable) throws IOException;

    public void setWritable(boolean writable) throws IOException;

    public void setHidden(boolean hidden) throws IOException;

    public Enumeration list() throws IOException;

    public Enumeration list(java.lang.String filter, boolean includeHidden) throws IOException;

    public void create() throws IOException;

    public void mkdir() throws IOException;

    public boolean exists();

    public boolean isDirectory();

    public void delete() throws IOException;

    public void rename(String newName) throws IOException;

    public void truncate(long byteOffset) throws IOException;

    public void setFileConnection(String fileName) throws IOException;

    public String getName();

    public String getPath();

    public String getURL();

    public long lastModified();
}