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

package com.nokia.mid.sound;

public class Sound {
    public static final int FORMAT_TONE = 1;
    public static final int FORMAT_WAV = 5;
    public static final int SOUND_PLAYING = 0;
    public static final int SOUND_STOPPED = 1;
    public static final int SOUND_UNINITIALIZED = 3;

    private SoundListener soundListener;
    private int state = SOUND_STOPPED;

    public Sound(byte[] data, int type) throws IllegalArgumentException, NullPointerException {
        init(data, type);
    }

    public Sound(int freq, long duration) throws IllegalArgumentException {
        init(freq, duration);
    }

    public void init(int freq, long duration) throws IllegalArgumentException  {
        if (duration <= 0) {
            throw new IllegalArgumentException();
        }
        // TODO: write method logic
    }

    public void init(byte[] data, int type) throws IllegalArgumentException, NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }
        // TODO: write method logic
    }

    public int getState() {
        return state;
    }

    public void play(int loop) throws IllegalArgumentException {
        if (loop < 0) {
            throw new IllegalArgumentException();
        }
        // TODO: write method logic
    }

    public void stop() {
        // TODO: write method logic
    }

    public void resume() {
        // TODO: write method logic
    }

    public void release() {
        // TODO: write method logic
    }

    public void setGain(int gain) {
        // TODO: write method logic
    }

    public int getGain() {
        // TODO: write method logic
        return 255;
    }

    public static int getConcurrentSoundCount(int type) throws IllegalArgumentException {
        // TODO: write method logic
        return 1;
    }

    public static int[] getSupportedFormats() {
        return new int[] { FORMAT_TONE, FORMAT_WAV };
    }

    public void setSoundListener(SoundListener listener) {
        soundListener = listener;
    }
}