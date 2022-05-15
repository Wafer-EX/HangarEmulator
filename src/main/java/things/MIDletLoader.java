package things;

import javax.microedition.midlet.MIDlet;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

public class MIDletLoader {
    private static MIDlet midlet;

    public static MIDlet loadMIDlet(String absolutePath) {
        try {
            MIDletResources.setJar(absolutePath);
            MIDletResources.initializeProperties();

            URL[] urls = {new URL("jar:file:" + absolutePath + "!/")};
            URLClassLoader loader = new URLClassLoader(urls);

            Class<?> cls = Class.forName(MIDletResources.getMIDletName(), true, loader);
            Constructor<?> constructor = cls.getConstructor();

            midlet = (MIDlet)constructor.newInstance();
            return midlet;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}