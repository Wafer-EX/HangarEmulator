/*
 * Copyright 2022-2023 Kirill Lomakin
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

package javax.microedition.media.protocol;

import javax.microedition.media.Controllable;
import java.io.IOException;

public abstract class DataSource implements Controllable {
    private final String locator;

    public DataSource(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }

    public abstract String getContentType();

    public abstract void connect() throws IOException;

    public abstract void disconnect();

    public abstract void start() throws IOException;

    public abstract void stop() throws java.io.IOException;

    public abstract SourceStream[] getStreams();
}