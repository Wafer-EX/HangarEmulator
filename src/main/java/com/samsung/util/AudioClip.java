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

package com.samsung.util;

import java.io.IOException;

public class AudioClip {
    public static final int TYPE_MMF = 1;
    public static final int TYPE_MP3 = 2;
    public static final int TYPE_MIDI = 3;

    public AudioClip(int type, byte[] audioData, int audioOffset, int audioLength) {
        // TODO: write method logic
    }

    public AudioClip(int type, String filename) throws IOException {
        // TODO: write method logic
    }

    public void play(int loop, int volume) {
        // TODO: write method logic
    }

    public void stop() {
        // TODO: write method logic
    }

    public void pause() {
        // TODO: write method logic
    }

    public void resume() {
        // TODO: write method logic
    }

    public static boolean isSupported() {
        return false;
    }
}