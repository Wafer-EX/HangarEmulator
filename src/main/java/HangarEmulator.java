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
import things.profiles.HangarProfileManager;
import things.ui.frames.HangarMainFrame;
import things.ui.components.HangarMainPanel;

import java.awt.*;
import java.io.File;
import java.util.Properties;

public class HangarEmulator {
    public static void main(String[] args) {
        // TODO: add args parsing
        try {
            FlatDarkLaf.setup();

            var profileManager = new HangarProfileManager(null);
            var properties = new Properties();
            properties.loadFromXML(HangarEmulator.class.getClassLoader().getResourceAsStream("app.xml"));

            HangarState.setProfileManager(profileManager);
            HangarState.setProgramFile(new File(HangarEmulator.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
            HangarState.setProperties(properties);

            var mainFrame = HangarMainFrame.getInstance();
            var mainPanel = new HangarMainPanel();

            if (args.length > 0 && new File(args[0]).isFile()) {
                var midletLoader = new MIDletLoader(args[0]);
                HangarState.setMIDletLoader(midletLoader);
                midletLoader.startMIDlet();
            }
            else {
                mainPanel.setPreferredSize(new Dimension(360, 360));
                mainFrame.add(mainPanel);
                mainFrame.pack();
                mainFrame.revalidate();
            }
            mainFrame.setVisible(true);
            mainFrame.setLocationRelativeTo(null);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}