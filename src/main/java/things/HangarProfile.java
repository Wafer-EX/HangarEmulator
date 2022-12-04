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

package things;

import things.enums.ScalingModes;
import things.ui.components.HangarCanvas;
import things.ui.frames.HangarMainFrame;
import things.ui.listeners.HangarKeyListener;
import things.utils.AudioUtils;
import things.utils.HangarCanvasUtils;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HangarProfile {
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

        var canvasPanel = HangarMainFrame.getInstance().getCanvasPanel();
        if (canvasPanel != null) {
            var keyListeners = canvasPanel.getKeyListeners();
            for (var keyListener : keyListeners) {
                if (keyListener instanceof HangarKeyListener hangarKeyListener) {
                    hangarKeyListener.getPressedKeys().clear();
                }
            }
        }
    }

    public ScalingModes getScalingMode() {
        return scalingMode;
    }

    public void setScalingMode(ScalingModes scalingMode) {
        this.scalingMode = scalingMode;

        SwingUtilities.invokeLater(() -> {
            var canvasPanel = HangarMainFrame.getInstance().getCanvasPanel();
            if (scalingMode == ScalingModes.ChangeResolution) {
                this.setResolution(canvasPanel.getSize());
            }
            canvasPanel.updateBufferTransformations();
        });
    }

    public Dimension getResolution() {
        return resolution;
    }

    public void setResolution(Dimension resolution) {
        this.resolution = resolution;

        SwingUtilities.invokeLater(() -> {
            var canvasPanel = HangarMainFrame.getInstance().getCanvasPanel();
            HangarCanvasUtils.fitBufferToResolution(canvasPanel, resolution);
        });
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
        SwingUtilities.invokeLater(() -> {
            var container = HangarMainFrame.getInstance().getContentPane();
            for (var component : container.getComponents()) {
                if (component instanceof HangarCanvas gamePanel) {
                    gamePanel.refreshSerialCallTimer();
                }
            }
        });
    }

    public boolean getCanvasClearing() {
        return canvasClearing;
    }

    public void setCanvasClearing(boolean canvasClearing) {
        this.canvasClearing = canvasClearing;
    }

    public boolean getAntiAliasing() {
        return antiAliasing;
    }

    public void setAntiAliasing(boolean antiAliasing) {
        this.antiAliasing = antiAliasing;
    }

    public boolean getWindowResizing() {
        return windowResizing;
    }

    public void setWindowResizing(boolean windowResizing) {
        this.windowResizing = windowResizing;
        HangarMainFrame.getInstance().setResizable(windowResizing);
    }

    public File getSoundbankFile() {
        return soundbankFile;
    }

    public void setSoundbankFile(File path) throws IOException, InvalidMidiDataException {
        var soundbankInputStream = new FileInputStream(path);
        var soundbank = MidiSystem.getSoundbank(soundbankInputStream);

        AudioUtils.setSoundbank(soundbank);
        this.soundbankFile = path;
    }
}