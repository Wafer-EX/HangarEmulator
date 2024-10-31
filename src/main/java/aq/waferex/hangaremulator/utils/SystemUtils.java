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

package aq.waferex.hangaremulator.utils;

import aq.waferex.hangaremulator.HangarState;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public final class SystemUtils {
    public static float getScalingInUnits() {
        float defaultDpi = 96;
        float dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        return dpi / defaultDpi;
    }

    public static void syncWithFrameRate() {
        if (HangarState.getGraphicsSettings().getFrameRate() != -1) {
            try {
                Thread.sleep(HangarState.getFrameRateInMilliseconds());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static File getProgramFile() throws URISyntaxException {
        var uri = SystemUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        return new File(uri);
    }

    public static void restartApp(String midletPath) {
        try {
            var javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            var command = new ArrayList<String>();

            if (System.getProperty("os.name").contains("nix")) {
                command.add("bash -c");
            }
            command.addAll(Arrays.asList(javaBin, "-jar", getProgramFile().toString()));
            command.addAll(ArgsUtils.getSettingsAsArgs());

            new ProcessBuilder(command).start();
            System.exit(0);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Properties getAppProperties() throws IOException {
        var properties = new Properties();
        var propertiesStream = SystemUtils.class.getClassLoader().getResourceAsStream("properties.xml");

        properties.loadFromXML(propertiesStream);
        return properties;
    }
}