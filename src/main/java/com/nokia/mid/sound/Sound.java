/*
 * Copyright 2022 Kirill Lomakin
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

package com.nokia.mid.sound;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class Sound {
    public static final int FORMAT_TONE = 1;
    public static final int FORMAT_WAV = 5;
    public static final int SOUND_PLAYING = 0;
    public static final int SOUND_STOPPED = 1;
    public static final int SOUND_UNINITIALIZED = 3;

    public Sound(byte[] data, int type) { }

    public Sound(int freq, long duration) { }

    public void init(int freq, long duration) throws NotImplementedException {
        throw new NotImplementedException("setGain");
    }

    public void init(byte[] data, int type) throws NotImplementedException {
        throw new NotImplementedException("setGain");
    }

    public int getState() {
        return SOUND_UNINITIALIZED;
    }

    public void play(int loop) throws NotImplementedException {
        throw new NotImplementedException("setGain");
    }

    public void stop() throws NotImplementedException {
        throw new NotImplementedException("setGain");
    }

    public void resume() throws NotImplementedException {
        throw new NotImplementedException("setGain");
    }

    public void release() throws NotImplementedException {
        throw new NotImplementedException("setGain");
    }

    public void setGain(int gain) throws NotImplementedException {
        throw new NotImplementedException("setGain");
    }

    public int getGain() {
        return 255;
    }

    public static int getConcurrentSoundCount(int type) {
        return 1;
    }

    public static int[] getSupportedFormats() {
        return new int[] { FORMAT_TONE, FORMAT_WAV };
    }

    public void setSoundListener(SoundListener listener) { }
}