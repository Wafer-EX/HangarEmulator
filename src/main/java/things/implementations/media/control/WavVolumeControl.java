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

package things.implementations.media.control;

import things.implementations.media.WavPlayer;

import javax.microedition.media.Control;
import javax.microedition.media.control.VolumeControl;

public class WavVolumeControl implements VolumeControl {
    private final WavPlayer player;
    private boolean mute = false;
    private int level;

    public WavVolumeControl(WavPlayer player) {
        this.player = player;
    }

    @Override
    public void setMute(boolean mute) {
        // TODO: write method logic
        this.mute = mute;
    }

    @Override
    public boolean isMuted() {
        return mute;
    }

    @Override
    public int setLevel(int level) {
        // TODO: write the volume setting for the player
        if (level < 0) {
            level = 0;
        }
        else if (level > 100) {
            level = 100;
        }
        return this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public Control[] getControls() {
        // TODO: write method logic
        return new Control[0];
    }

    @Override
    public Control getControl(String controlType) {
        // TODO: write method logic
        return null;
    }
}