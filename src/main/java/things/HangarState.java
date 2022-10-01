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

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class HangarState {
    private static File programFile;
    private static final HangarProfile profile = new HangarProfile();

    public static HangarProfile getProfile() {
        return profile;
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
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void syncWithFrameRate() {
        if (profile.getFrameRate() != -1) {
            try {
                Thread.sleep(frameRateInMilliseconds());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static int frameRateInMilliseconds() {
        return 1000 / profile.getFrameRate();
    }

    public static Graphics2D applyRenderingHints(Graphics graphics) {
        var graphics2d = (Graphics2D) graphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, profile.getAntiAliasing() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        return graphics2d;
    }
}