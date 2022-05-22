import things.*;

import javax.swing.*;
import java.io.File;
import java.net.URISyntaxException;

public class HangarEmulator {
    public static void main(String[] args) throws URISyntaxException {
        JFrame mainWindow = new JFrame();
        mainWindow.setTitle("Hangar Emulator");
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainWindow.setJMenuBar(new HangarMenuBar());
        mainWindow.add(HangarPanel.getInstance());
        mainWindow.addKeyListener(new HangarKeyListener());
        mainWindow.pack();
        mainWindow.setVisible(true);

        if (args.length > 0 && new File(args[0]).isFile()) {
            try {
                var midlet = MIDletLoader.loadMIDlet(args[0]);
                mainWindow.setTitle(System.getProperty("MIDlet-Name"));
                mainWindow.setIconImage(MIDletResources.getMIDletIcon());
                midlet.startApp();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        var programFile = new File(HangarEmulator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        HangarState.setProgramFile(programFile);
    }
}