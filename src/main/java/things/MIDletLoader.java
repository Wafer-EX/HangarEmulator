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

import javax.microedition.midlet.MIDlet;
import java.net.URL;

public class MIDletLoader {
    private static MIDlet midlet;
    private static String midletPath;

    public static void loadMIDlet(String absolutePath) {
        try {
            MIDletResources.setJar(absolutePath);
            MIDletResources.initializeMIDletProperties();

            URL[] urls = { new URL("jar:file:" + absolutePath + "!/") };
            var classLoader = new MIDletClassLoader(urls);

            var mainClass = Class.forName(MIDletResources.getMainClassName(), true, classLoader);
            var constructor = mainClass.getConstructor();
            constructor.setAccessible(true);

            midlet = (MIDlet) constructor.newInstance();
            midletPath = absolutePath;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void startLoadedMIDlet() {
        try {
            HangarFrame.getInstance().setTitle(System.getProperty("MIDlet-Name"));
            HangarFrame.getInstance().setIconImage(MIDletResources.getIconFromJar());
            midlet.startApp();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static MIDlet getLastLoaded() {
        return midlet;
    }

    public static String getLastLoadedPath() {
        return midletPath;
    }
}