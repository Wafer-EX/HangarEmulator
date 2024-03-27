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

import aq.waferex.hangaremulator.profiles.HangarProfileManager;
import aq.waferex.hangaremulator.ui.frames.HangarMainFrame;

import java.awt.*;
import java.util.Properties;

public class HangarState {
    private static HangarMainFrame mainFrame;
    private static Properties properties;
    private static MIDletLoader midletLoader;
    private static HangarProfileManager profileManager;

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

    public static HangarProfileManager getProfileManager() {
        return profileManager;
    }

    public static void setProfileManager(HangarProfileManager manager) {
        profileManager = manager;
    }

    public static void syncWithFrameRate() {
        if (profileManager.getCurrentProfile().getFrameRate() != -1) {
            try {
                Thread.sleep(frameRateInMilliseconds());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static int frameRateInMilliseconds() {
        return 1000 / profileManager.getCurrentProfile().getFrameRate();
    }

    public static Graphics2D applyAntiAliasing(Graphics graphics) {
        var graphics2d = (Graphics2D) graphics;
        var hintValue = profileManager.getCurrentProfile().getAntiAliasing() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, hintValue);
        return graphics2d;
    }
}