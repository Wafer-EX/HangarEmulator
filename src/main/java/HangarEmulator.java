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

import com.formdev.flatlaf.FlatDarkLaf;
import things.*;
import things.ui.frames.HangarMainFrame;
import things.ui.components.HangarMainPanel;

import java.awt.*;
import java.io.File;
import java.util.Locale;

public class HangarEmulator {
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        }
        catch (NoClassDefFoundError error) {
            error.printStackTrace();
        }

        System.setProperty("hangaremulator.version", "0.3-alpha");
        System.setProperty("hangaremulator.github", "https://github.com/Lisowolf/HangarEmulator");
        System.setProperty("hangaremulator.author", "Kirill Lomakin (minebuilder445@gmail.com)");

        System.setProperty("microedition.profiles", "MIDP-2.0");
        System.setProperty("microedition.platform", "HangarEmulator");
        System.setProperty("microedition.locale", Locale.getDefault().toLanguageTag());

        try {
            if (args.length > 0 && new File(args[0]).isFile()) {
                MIDletLoader.loadMIDlet(args[0]);
                MIDletLoader.startLoadedMIDlet();
            }
            else {
                var mainFrame = HangarMainFrame.getInstance();
                var mainPanel = new HangarMainPanel();

                mainPanel.setPreferredSize(new Dimension(360, 360));
                mainFrame.add(mainPanel);
                mainFrame.pack();
                mainFrame.revalidate();
            }
            HangarState.setProgramFile(new File(HangarEmulator.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
            HangarMainFrame.getInstance().setVisible(true);
            HangarMainFrame.getInstance().setLocationRelativeTo(null);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}