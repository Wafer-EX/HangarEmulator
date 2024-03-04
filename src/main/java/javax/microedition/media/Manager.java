/*
 * Copyright 2022-2024 Wafer EX
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

package javax.microedition.media;

import things.implementations.media.MidiPlayer;
import things.implementations.media.WavPlayer;

import java.io.IOException;
import java.io.InputStream;

public final class Manager {
    public static String[] getSupportedContentTypes(String protocol) {
        return new String[] {
                "audio/wav",
                "audio/x-wav",
                "audio/midi",
                "audio/x-midi"
        };
    }

    public static String[] getSupportedProtocols(String content_type) {
        // TODO: rewrite method logic
        return new String[] { };
    }

    public static Player createPlayer(InputStream stream, String type) throws IOException, MediaException {
        if (stream == null) {
            throw new IllegalArgumentException();
        }

        // TODO: write logic for other types
        return switch (type) {
            case "audio/midi" -> new MidiPlayer(stream);
            case "audio/x-wav" -> new WavPlayer(stream);
            default -> throw new MediaException();
        };
    }
}