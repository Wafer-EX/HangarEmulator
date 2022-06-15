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

import java.io.File;

public class HangarEmulator {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("microedition.profiles", "MIDP-2.0");
        System.setProperty("microedition.platform", "HangarEmulator");

        try {
            if (args.length > 0 && new File(args[0]).isFile()) {
                MIDletLoader.loadMIDlet(args[0]);
                MIDletLoader.startLoadedMIDlet();
            }
            else {
                var hangarLabel = HangarLabel.getInstance();
                hangarLabel.setText("Please select a file.");
                HangarFrame.getInstance().setLabel(hangarLabel);
            }
            HangarState.setProgramFile(new File(HangarEmulator.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
            HangarFrame.getInstance().setVisible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}