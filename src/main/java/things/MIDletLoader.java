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
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

public class MIDletLoader {
    private static MIDlet midlet;
    private static String midletPath;

    public static MIDlet loadMIDlet(String absolutePath) {
        try {
            MIDletResources.setJar(absolutePath);
            MIDletResources.initializeProperties();

            URL[] urls = { new URL("jar:file:" + absolutePath + "!/") };
            URLClassLoader loader = new URLClassLoader(urls);

            Class<?> cls = Class.forName(MIDletResources.getMIDletName(), true, loader);
            Constructor<?> constructor = cls.getConstructor();
            constructor.setAccessible(true);

            midlet = (MIDlet)constructor.newInstance();
            midletPath = absolutePath;
            return midlet;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void startLoadedMIDlet() {
        try {
            var hangarFrame = HangarFrame.getInstance();
            hangarFrame.setTitle(System.getProperty("MIDlet-Name"));
            hangarFrame.setIconImage(MIDletResources.getMIDletIcon());
            hangarFrame.setHangarPanel();
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