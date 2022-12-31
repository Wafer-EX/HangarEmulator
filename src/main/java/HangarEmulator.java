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
import things.utils.SystemUtils;

import java.io.File;

public class HangarEmulator {
    public static void main(String[] args) {
        // TODO: add args parsing
        try {
            System.setProperty("sun.java2d.opengl", "true");
            FlatDarkLaf.setup();

            HangarState.setProfileManager(new HangarProfileManager(null));
            HangarState.setProperties(SystemUtils.getAppProperties());

            if (args.length > 0 && new File(args[0]).isFile()) {
                var midletLoader = new MIDletLoader(args[0]);
                HangarState.setMIDletLoader(midletLoader);
                midletLoader.startMIDlet();
            }

            var mainFrame = new HangarMainFrame();
            HangarState.setMainFrame(mainFrame);
            mainFrame.setVisible(true);
            mainFrame.setLocationRelativeTo(null);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}