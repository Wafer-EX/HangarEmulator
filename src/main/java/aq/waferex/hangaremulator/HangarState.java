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

package aq.waferex.hangaremulator;

import aq.waferex.hangaremulator.enums.ScalingModes;
import aq.waferex.hangaremulator.settings.*;
import aq.waferex.hangaremulator.ui.frames.HangarMainFrame;

import java.awt.*;
import java.io.File;
import java.util.Properties;

public class HangarState {
    private static HangarMainFrame mainFrame;
    private static Properties properties;
    private static MIDletLoader midletLoader;

    private static final HangarGraphicsSettings graphicsSettings = new HangarGraphicsSettings();
    private static final HangarAudioSettings audioSettings = new HangarAudioSettings();
    private static final HangarInputSettings inputSettings = new HangarInputSettings();
    private static final HangarFontSettings fontSettings = new HangarFontSettings();

    public static HangarMainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(HangarMainFrame mainFrame) {
        HangarState.mainFrame = mainFrame;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        HangarState.properties = properties;
    }

    public static MIDletLoader getMIDletLoader() {
        return midletLoader;
    }

    public static void setMIDletLoader(MIDletLoader loader) {
        midletLoader = loader;
    }

    public static HangarGraphicsSettings getGraphicsSettings() {
        return graphicsSettings;
    }

    public static HangarAudioSettings getAudioSettings() {
        return audioSettings;
    }

    public static HangarInputSettings getInputSettings() {
        return inputSettings;
    }

    public static HangarFontSettings getFontSettings() {
        return fontSettings;
    }

    public static int getFrameRateInMilliseconds() {
        return 1000 / graphicsSettings.getFrameRate();
    }

    public static void initSettingsFromArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String argument = args[i];

            switch (argument) {
                case "--screen-clearing" -> HangarState.getGraphicsSettings().setScreenClearing(true);
                case "--vector-anti-aliasing" -> HangarState.getGraphicsSettings().setVectorAntiAliasing(true);
                case "--fps" -> {
                    int fps = Integer.parseInt(args[i + 1]);
                    HangarState.getGraphicsSettings().setFrameRate(fps);
                    i++;
                }
                case "--scaling-mode" -> {
                    String scalingModeString = args[i + 1];
                    if (scalingModeString.equals("none")) {
                        HangarState.getGraphicsSettings().setScalingMode(ScalingModes.None);
                    }
                    if (scalingModeString.equals("fit")) {
                        HangarState.getGraphicsSettings().setScalingMode(ScalingModes.Fit);
                    }
                    if (scalingModeString.equals("change-resolution")) {
                        HangarState.getGraphicsSettings().setScalingMode(ScalingModes.ChangeResolution);
                    }
                    i++;
                }
                case "--resolution" -> {
                    int width = Integer.parseInt(args[i + 1]);
                    int height = Integer.parseInt(args[i + 2]);
                    HangarState.getGraphicsSettings().setResolution(new Dimension(width, height));
                    i += 2;
                }
                case "--interpolation" -> HangarState.getGraphicsSettings().setInterpolation(true);
                case "--touchscreen-support" -> HangarState.getInputSettings().setTouchscreenInput(true);
                case "--midlet" -> {
                    String filePath = args[i + 1];
                    if (new File(filePath).isFile()) {
                        midletLoader = new MIDletLoader(filePath);
                    }
                    // TODO: throw exception else
                    i++;
                }
            }
        }

        if (midletLoader != null) {
            midletLoader.startMIDlet();
        }
    }
}