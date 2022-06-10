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

import things.*;

import java.io.File;
import java.net.URISyntaxException;

public class HangarEmulator {
    public static void main(String[] args) throws URISyntaxException {
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("microedition.profiles", "MIDP-2.0");
        System.setProperty("microedition.platform", "HangarEmulator");

        if (args.length > 0 && new File(args[0]).isFile()) {
            try {
                MIDletLoader.loadMIDlet(args[0]);
                MIDletLoader.startLoadedMIDlet();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            HangarFrame.getInstance().setLabel(new HangarLabel("Please select a file."));
        }

        var programFile = new File(HangarEmulator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        HangarState.setProgramFile(programFile);
        HangarFrame.getInstance().setVisible(true);
    }
}