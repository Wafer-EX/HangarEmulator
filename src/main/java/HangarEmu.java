import things.CanvasPanel;
import things.MIDletKeyListener;
import things.MIDletLoader;
import things.MIDletResources;

import javax.microedition.midlet.MIDlet;
import javax.swing.*;

public class HangarEmu {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame("HangarEmu");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.add(CanvasPanel.getInstance());
        mainWindow.addKeyListener(new MIDletKeyListener());
        mainWindow.pack();
        mainWindow.setVisible(true);

        JFileChooser file = new JFileChooser();
        file.showDialog(null, "Select MIDlet");

        SwingUtilities.invokeLater(() -> {
            try {
                MIDlet midlet = MIDletLoader.loadMIDlet(file.getSelectedFile().getAbsolutePath());
                mainWindow.setTitle(System.getProperty("MIDlet-Name"));
                mainWindow.setIconImage(MIDletResources.getMIDletIcon());
                midlet.startApp();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                mainWindow.dispose();
            }
        });
    }
}