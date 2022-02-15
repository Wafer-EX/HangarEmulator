package things;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class MIDletResources {
    private static URLClassLoader classLoader;
    private static JarFile jarFile;

    public static void setJar(String absolutePath) {
        try {
            URL[] urls = {new URL("jar:file:" + absolutePath + "!/")};
            classLoader = new URLClassLoader(urls);
            jarFile = new JarFile(new File(absolutePath));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static InputStream getResourceFromJar(String resourcePath) {
        try {
            resourcePath = resourcePath.substring(1);
            return classLoader.getResourceAsStream(resourcePath);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getMIDletName() {
        try {
            var manifest = jarFile.getManifest();
            var attributes = manifest.getMainAttributes();
            var info = attributes.getValue("MIDlet-1").split(",");
            return info[2].trim();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
