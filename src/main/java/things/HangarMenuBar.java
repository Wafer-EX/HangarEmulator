package things;

import javax.microedition.midlet.MIDletStateChangeException;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class HangarMenuBar extends JMenuBar {
    public HangarMenuBar() {
        addMIDletMenu();
        addHelpMenu();
    }

    private void addMIDletMenu() {
        var midletMenu = new JMenu("MIDlet");
        var loadMenuItem = new JMenuItem("Load MIDlet");
        var restartMenuItem = new JMenuItem("Restart");
        var pauseMenuItem = new JMenuItem("Pause");
        var settingsMenuItem = new JMenuItem("Settings");
        var exitMenuItem = new JMenuItem("Exit");

        loadMenuItem.addActionListener(event -> {
            var mainWindow = (JFrame) SwingUtilities.getWindowAncestor(this);
            var fileChooser = new JFileChooser();
            fileChooser.showDialog(null, "Select MIDlet");

            try {
                var currentMidlet = MIDletLoader.getLastLoaded();
                if (currentMidlet != null) {
                    currentMidlet.setExitBlock(true);
                    currentMidlet.destroyApp(true);
                }
            }
            catch (MIDletStateChangeException e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(() -> {
                try {
                    if (fileChooser.getSelectedFile() != null) {
                        var midlet = MIDletLoader.loadMIDlet(fileChooser.getSelectedFile().getAbsolutePath());
                        mainWindow.setTitle(System.getProperty("MIDlet-Name"));
                        mainWindow.setIconImage(MIDletResources.getMIDletIcon());
                        midlet.startApp();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    mainWindow.dispose();
                }
            });
        });
        restartMenuItem.addActionListener(event -> {
            try {
                var currentMidlet = MIDletLoader.getLastLoaded();
                currentMidlet.setExitBlock(true);
                currentMidlet.destroyApp(true);
                currentMidlet.startApp();
                currentMidlet.setExitBlock(false);
            }
            catch (MIDletStateChangeException e) {
                e.printStackTrace();
            }
        });

        pauseMenuItem.addActionListener(event -> {
            MIDletLoader.getLastLoaded().pauseApp();
        });

        settingsMenuItem.addActionListener(event -> {
            var settingsWindow = new HangarSettings();
            settingsWindow.setVisible(true);
        });

        exitMenuItem.addActionListener(event -> System.exit(0));

        midletMenu.add(loadMenuItem);
        midletMenu.add(new JSeparator());
        midletMenu.add(pauseMenuItem);
        midletMenu.add(restartMenuItem);
        midletMenu.add(new JSeparator());
        midletMenu.add(settingsMenuItem);
        midletMenu.add(exitMenuItem);
        this.add(midletMenu);
    }

    private void addHelpMenu() {
        var helpMenu = new JMenu("Help");

        var githubLinkMenuItem = new JMenuItem("GitHub page");
        githubLinkMenuItem.addActionListener(event -> {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/Lisowolf/HangarEmulator").toURI());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        helpMenu.add(githubLinkMenuItem);
        this.add(helpMenu);
    }
}