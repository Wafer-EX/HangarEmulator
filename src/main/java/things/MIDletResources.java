package things;

import javax.imageio.ImageIO;
import java.awt.*;
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
            if (resourcePath == null) {
                throw new IllegalArgumentException();
            }
            else {
                if (resourcePath.charAt(0) == '/') {
                    resourcePath = resourcePath.substring(1);
                }
                return classLoader.getResourceAsStream(resourcePath);
            }
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

    public static Image getMIDletIcon() {
        try {
            var manifest = jarFile.getManifest();
            var attributes = manifest.getMainAttributes();
            var iconPath = attributes.getValue("MIDlet-Icon");
            var inputStream = getResourceFromJar(iconPath);
            return ImageIO.read(inputStream);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void initializeProperties() {
        try {
            var manifest = jarFile.getManifest();
            var attributes = manifest.getMainAttributes().entrySet();
            for (var attribute : attributes) {
                System.setProperty(attribute.getKey().toString(), attribute.getValue().toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}