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

import things.enums.Keyboards;
import things.enums.ScalingModes;
import things.utils.HangarPanelUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class HangarState {
    private static Keyboards selectedKeyboard = Keyboards.Nokia;
    private static ScalingModes scalingMode = ScalingModes.None;
    private static File programFile;
    private static Dimension currentResolution = new Dimension(240, 320);
    private static boolean clearScreen;
    private static boolean enableAntiAliasing;
    private static int frameRate = 60;

    public static Dimension getResolution() {
        return currentResolution;
    }

    public static void setResolution(Dimension resolution) {
        currentResolution = resolution;
    }

    public static int getFrameRate() {
        return frameRate;
    }

    public static void setFrameRate(int frameRate) {
        HangarState.frameRate = frameRate;
    }

    public static boolean getAntiAliasing() {
        return enableAntiAliasing;
    }

    public static void setAntiAliasing(boolean antiAliasing) {
        enableAntiAliasing = antiAliasing;
    }

    public static Keyboards getKeyboard() {
        return selectedKeyboard;
    }

    public static void setKeyboard(Keyboards keyboard) {
        selectedKeyboard = keyboard;
        var keyListeners = HangarPanel.getInstance().getKeyListeners();
        if (keyListeners.length > 0) {
            for (var keyListener : keyListeners) {
                if (keyListener instanceof HangarKeyListener hangarKeyListener) {
                    hangarKeyListener.getPressedKeys().clear();
                }
            }
        }
    }

    public static ScalingModes getScalingMode() {
        return scalingMode;
    }

    public static void setScalingMode(ScalingModes mode) {
        scalingMode = mode;
        var hangarPanel = HangarPanel.getInstance();
        if (scalingMode == ScalingModes.ChangeResolution) {
            var hangarLabel = HangarLabel.getInstance();
            var resolution = MIDletLoader.isLoaded() ? hangarPanel.getSize() : hangarLabel.getSize();
            HangarPanelUtils.fitBufferToNewResolution(hangarPanel, resolution);
        }
        hangarPanel.updateBufferTransformations();
    }

    public static boolean getCanvasClearing() {
        return clearScreen;
    }

    public static void setCanvasClearing(boolean clear) {
        clearScreen = clear;
    }

    public static void setProgramFile(File file) {
        programFile = file;
    }

    public static void restartApp(String midletPath) {
        try {
            var javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            var command = new ArrayList<String>();

            if (System.getProperty("os.name").contains("nix")) {
                command.add("bash -c");
            }
            command.addAll(Arrays.asList(javaBin, "-jar", programFile.toString()));
            if (midletPath != null) {
                command.add(midletPath);
            }
            new ProcessBuilder(command).start();
            System.exit(0);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void syncWithFrameRate() {
        if (HangarState.getFrameRate() != -1) {
            try {
                Thread.sleep(frameRateInMilliseconds());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static int frameRateInMilliseconds() {
        return 1000 / HangarState.getFrameRate();
    }

    public static Graphics2D applyRenderingHints(Graphics graphics) {
        var graphics2d = (Graphics2D) graphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, enableAntiAliasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        return graphics2d;
    }
}