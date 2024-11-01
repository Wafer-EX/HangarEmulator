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
    public static final String ARGUMENT_SCREEN_CLEARING = "--screen-clearing";
    public static final String ARGUMENT_VECTOR_ANTI_ALIASING = "--vector-anti-aliasing";
    public static final String ARGUMENT_FRAME_RATE = "--fps";
    public static final String ARGUMENT_SCALING_MODE = "--scaling-mode";
    public static final String ARGUMENT_RESOLUTION = "--resolution";
    public static final String ARGUMENT_INTERPOLATION = "--interpolation";
    public static final String ARGUMENT_TOUCHSCREEN_SUPPORT = "--touchscreen-support";
    public static final String ARGUMENT_MIDLET = "--midlet";

    // TODO: refactor this
    public static void initSettingsFromArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String argument = args[i];

            switch (argument) {
                case ARGUMENT_SCREEN_CLEARING -> HangarState.getGraphicsSettings().setScreenClearing(true);
                case ARGUMENT_VECTOR_ANTI_ALIASING -> HangarState.getGraphicsSettings().setVectorAntiAliasing(true);
                case ARGUMENT_FRAME_RATE -> {
                    int fps = Integer.parseInt(args[i + 1]);
                    HangarState.getGraphicsSettings().setFrameRate(fps);
                    i++;
                }
                case ARGUMENT_SCALING_MODE -> {
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
                case ARGUMENT_RESOLUTION -> {
                    int width = Integer.parseInt(args[i + 1]);
                    int height = Integer.parseInt(args[i + 2]);
                    HangarState.getGraphicsSettings().setResolution(new Dimension(width, height));
                    i += 2;
                }
                case ARGUMENT_INTERPOLATION -> HangarState.getGraphicsSettings().setInterpolation(true);
                case ARGUMENT_TOUCHSCREEN_SUPPORT -> HangarState.getInputSettings().setTouchscreenInput(true);
                case ARGUMENT_MIDLET -> {
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
            argsList.add(ARGUMENT_SCREEN_CLEARING);
        }

        if (HangarState.getGraphicsSettings().getVectorAntiAliasing()) {
            argsList.add(ARGUMENT_VECTOR_ANTI_ALIASING);
        }

        argsList.add(ARGUMENT_FRAME_RATE);
        argsList.add(String.valueOf(HangarState.getGraphicsSettings().getFrameRate()));

        argsList.add(ARGUMENT_SCALING_MODE);
        if (HangarState.getGraphicsSettings().getScalingMode() == ScalingModes.None) {
            argsList.add("none");
        }
        else if (HangarState.getGraphicsSettings().getScalingMode() == ScalingModes.Fit) {
            argsList.add("fit");
        }
        else if (HangarState.getGraphicsSettings().getScalingMode() == ScalingModes.ChangeResolution) {
            argsList.add("change-resolution");
        }

        argsList.add(ARGUMENT_RESOLUTION);
        argsList.add(String.valueOf(HangarState.getGraphicsSettings().getResolution().width));
        argsList.add(String.valueOf(HangarState.getGraphicsSettings().getResolution().height));

        if (HangarState.getGraphicsSettings().getInterpolation()) {
            argsList.add(ARGUMENT_INTERPOLATION);
        }

        if (HangarState.getInputSettings().getTouchscreenInput()) {
            argsList.add(ARGUMENT_TOUCHSCREEN_SUPPORT);
        }

        if (HangarState.getMIDletLoader().getMIDlet() != null) {
            argsList.add(ARGUMENT_MIDLET);
            argsList.add(HangarState.getMIDletLoader().getMIDletPath());
        }

        return argsList;
    }
}