package things;

import things.enums.Keyboards;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class HangarState {
    private static Keyboards selectedKeyboard = Keyboards.Default;
    private static File programFile;
    private static Dimension currentResolution = new Dimension(240, 320);

    public static Keyboards getKeyboard() {
        return selectedKeyboard;
    }

    public static Dimension getResolution() {
        return currentResolution;
    }

    public static void setResolution(Dimension resolution) {
        currentResolution = resolution;
    }

    public static void setKeyboard(Keyboards keyboard) {
        selectedKeyboard = keyboard;
        var keyListeners = HangarPanel.getInstance().getKeyListeners();
        if (keyListeners.length > 0) {
            for (var keyListener : keyListeners) {
                if (keyListener instanceof HangarKeyListener) {
                    var hangarKeyListener = (HangarKeyListener) keyListener;
                    hangarKeyListener.getPressedKeys().clear();
                }
            }
        }
    }

    public static void setProgramFile(File file) {
        programFile = file;
    }

    public static void restartApp(String midletPath) {
        try {
            var javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            var command = new ArrayList<String>();

            if (System.getProperty("os.name").contains("nix")) {
                command.add("bash -c");
            }
            command.addAll(Arrays.asList(javaBin, "-jar", programFile.toString()));
            if (midletPath != null) {
                command.add(midletPath);
            }
            new ProcessBuilder(command).start();
            System.exit(0);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}