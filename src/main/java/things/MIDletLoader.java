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

import things.ui.frames.HangarMainFrame;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import java.net.URL;
import java.net.URLClassLoader;

public class MIDletLoader {
    private MIDlet midlet;
    private String midletPath;

    public MIDletLoader(String absolutePath) {
        try {
            JarResources.setJar(absolutePath);
            JarResources.initializeProperties();

            URL[] urls = { new URL("jar:file:" + absolutePath + "!/") };
            ClassLoader classLoader;

            try {
                classLoader = new MIDletClassLoader(urls);
            }
            catch (NoClassDefFoundError error) {
                error.printStackTrace();
                classLoader = new URLClassLoader(urls);
            }

            var mainClass = Class.forName(JarResources.getMainClassName(), true, classLoader);
            var constructor = mainClass.getConstructor();
            constructor.setAccessible(true);

            midlet = (MIDlet) constructor.newInstance();
            midletPath = absolutePath;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void startMIDlet() {
        try {
            if (midlet != null) {
                var hangarFrame = HangarMainFrame.getInstance();
                hangarFrame.setTitle(System.getProperty("MIDlet-Name"));
                hangarFrame.setIconImage(JarResources.getIcon());

                new Thread(() -> {
                    try {
                        midlet.startApp();
                    }
                    catch (MIDletStateChangeException exception) {
                        exception.printStackTrace();
                    }
                }).start();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public MIDlet getMIDlet() {
        return midlet;
    }

    public String getMIDletPath() {
        return midletPath;
    }
}