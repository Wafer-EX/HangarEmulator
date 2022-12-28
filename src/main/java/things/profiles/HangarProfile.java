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

package things.profiles;

import things.HangarKeyCodes;
import things.enums.ScalingModes;
import things.ui.frames.HangarMainFrame;
import things.ui.listeners.HangarProfileListener;
import things.ui.listeners.events.HangarProfileEvent;
import things.utils.AudioUtils;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HangarProfile {
    private final ArrayList<HangarProfileListener> profileListeners = new ArrayList<>();
    private HangarKeyCodes midletKeyCodes = HangarKeyCodes.MIDLET_KEYCODES_NOKIA;
    private ScalingModes scalingMode = ScalingModes.None;
    private Dimension resolution = new Dimension(240, 320);
    private int frameRate = 60;
    private boolean canvasClearing = false;
    private boolean antiAliasing = false;
    private boolean windowResizing = false;
    private File soundbankFile = null;

    public HangarKeyCodes getMidletKeyCodes() {
        return midletKeyCodes;
    }

    public void setMidletKeyCodes(HangarKeyCodes keyCodes) {
        this.midletKeyCodes = keyCodes;
        for (var profileListener : profileListeners) {
            profileListener.profileStateChanged(new HangarProfileEvent(this, HangarProfileEvent.MIDLET_KEYCODES_CHANGED, keyCodes));
        }
    }

    public ScalingModes getScalingMode() {
        return scalingMode;
    }

    public void setScalingMode(ScalingModes scalingMode) {
        this.scalingMode = scalingMode;
        for (var profileListener : profileListeners) {
            profileListener.profileStateChanged(new HangarProfileEvent(this, HangarProfileEvent.SCALING_MODE_CHANGED, scalingMode));
        }
    }

    public Dimension getResolution() {
        return resolution;
    }

    public void setResolution(Dimension resolution) {
        this.resolution = resolution;
        for (var profileListener : profileListeners) {
            profileListener.profileStateChanged(new HangarProfileEvent(this, HangarProfileEvent.RESOLUTION_CHANGED, resolution));
        }
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
        for (var profileListener : profileListeners) {
            profileListener.profileStateChanged(new HangarProfileEvent(this, HangarProfileEvent.FRAME_RATE_CHANGED, frameRate));
        }
    }

    public boolean getCanvasClearing() {
        return canvasClearing;
    }

    public void setCanvasClearing(boolean canvasClearing) {
        this.canvasClearing = canvasClearing;
        for (var profileListener : profileListeners) {
            profileListener.profileStateChanged(new HangarProfileEvent(this, HangarProfileEvent.CANVAS_CLEARING_CHANGED, canvasClearing));
        }
    }

    public boolean getAntiAliasing() {
        return antiAliasing;
    }

    public void setAntiAliasing(boolean antiAliasing) {
        this.antiAliasing = antiAliasing;
        for (var profileListener : profileListeners) {
            profileListener.profileStateChanged(new HangarProfileEvent(this, HangarProfileEvent.ANTI_ALIASING_CHANGED, antiAliasing));
        }
    }

    public boolean getWindowResizing() {
        return windowResizing;
    }

    public void setWindowResizing(boolean windowResizing) {
        this.windowResizing = windowResizing;
        for (var profileListener : profileListeners) {
            profileListener.profileStateChanged(new HangarProfileEvent(this, HangarProfileEvent.WINDOW_RESIZING_CHANGED, windowResizing));
        }

        // TODO: use event system
        HangarMainFrame.getInstance().setResizable(windowResizing);
    }

    public File getSoundbankFile() {
        return soundbankFile;
    }

    public void setSoundbankFile(File path) throws IOException, InvalidMidiDataException {
        // TODO: use event system
        if (path != null) {
            var soundbankInputStream = new FileInputStream(path);
            var soundbank = MidiSystem.getSoundbank(soundbankInputStream);

            AudioUtils.setSoundbank(soundbank);
        }

        this.soundbankFile = path;
        for (var profileListener : profileListeners) {
            profileListener.profileStateChanged(new HangarProfileEvent(this, HangarProfileEvent.SOUNDBANK_CHANGED, path));
        }
    }

    public void addProfileListener(HangarProfileListener listener) {
        this.profileListeners.add(listener);
    }
}