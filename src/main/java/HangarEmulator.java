import things.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;

public class HangarEmulator {
    public static void main(String[] args) throws URISyntaxException {
        JFrame mainWindow = new JFrame();
        HangarState.setWindow(mainWindow);

        mainWindow.setTitle("Hangar Emulator");
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setJMenuBar(new HangarMenuBar());

        if (args.length > 0 && new File(args[0]).isFile()) {
            try {
                MIDletLoader.loadMIDlet(args[0]);
                MIDletLoader.startLastLoadedMIDlet();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            var label = new JLabel("Please select a file.");
            HangarState.setLabel(label);

            label.setPreferredSize(new Dimension(240, 320));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            mainWindow.add(label);
        }

        var programFile = new File(HangarEmulator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        HangarState.setProgramFile(programFile);

        mainWindow.pack();
        mainWindow.setVisible(true);
    }
}