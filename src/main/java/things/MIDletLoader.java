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