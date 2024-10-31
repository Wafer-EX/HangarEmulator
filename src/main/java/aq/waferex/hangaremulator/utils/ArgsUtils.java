/*
 * Copyright 2024 Wafer EX
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

package aq.waferex.hangaremulator.utils;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.MIDletLoader;
import aq.waferex.hangaremulator.enums.ScalingModes;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArgsUtils {
    // TODO: refactor this
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
                        HangarState.setMIDletLoader(new MIDletLoader(filePath));
                    }
                    // TODO: throw exception else
                    i++;
                }
            }
        }

        if (HangarState.getMIDletLoader() != null) {
            HangarState.getMIDletLoader().startMIDlet();
        }
    }

    // TODO: refactor this
    public static List<String> getSettingsAsArgs() {
        var argsList = new ArrayList<String>();

        if (HangarState.getGraphicsSettings().getScreenClearing()) {
            argsList.add("--screen-clearing");
        }

        if (HangarState.getGraphicsSettings().getVectorAntiAliasing()) {
            argsList.add("--vector-anti-aliasing");
        }

        argsList.add("--fps");
        argsList.add(String.valueOf(HangarState.getGraphicsSettings().getFrameRate()));

        argsList.add("--scaling-mode");
        if (HangarState.getGraphicsSettings().getScalingMode() == ScalingModes.None) {
            argsList.add("none");
        }
        else if (HangarState.getGraphicsSettings().getScalingMode() == ScalingModes.Fit) {
            argsList.add("fit");
        }
        else if (HangarState.getGraphicsSettings().getScalingMode() == ScalingModes.ChangeResolution) {
            argsList.add("change-resolution");
        }

        argsList.add("--resolution");
        argsList.add(String.valueOf(HangarState.getGraphicsSettings().getResolution().width));
        argsList.add(String.valueOf(HangarState.getGraphicsSettings().getResolution().height));

        if (HangarState.getGraphicsSettings().getInterpolation()) {
            argsList.add("--interpolation");
        }

        if (HangarState.getInputSettings().getTouchscreenInput()) {
            argsList.add("--touchscreen-support");
        }

        if (HangarState.getMIDletLoader().getMIDlet() != null) {
            argsList.add("--midlet");
            argsList.add(HangarState.getMIDletLoader().getMIDletPath());
        }

        return argsList;
    }
}