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

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class HangarState {
    private static Keyboards selectedKeyboard = Keyboards.Default;
    private static File programFile;
    private static Dimension currentResolution = new Dimension(240, 320);

    public static Keyboards getKeyboard() {
        return selectedKeyboard;
    }

    public static Dimension getResolution() {
        return currentResolution;
    }

    public static void setResolution(Dimension resolution) {
        currentResolution = resolution;
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
}