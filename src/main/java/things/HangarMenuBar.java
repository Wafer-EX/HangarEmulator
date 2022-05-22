package things;

import things.enums.Keyboards;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class HangarMenuBar extends JMenuBar {
    public HangarMenuBar() {
        addMIDletMenu();
        addOptionsMenu();
        addHelpMenu();
    }

    private void addMIDletMenu() {
        var midletMenu = new JMenu("MIDlet");
        var loadMenuItem = new JMenuItem("Load MIDlet");
        var restartMenuItem = new JMenuItem("Restart");
        var pauseMenuItem = new JMenuItem("Call pauseApp()");
        var exitMenuItem = new JMenuItem("Exit");

        loadMenuItem.addActionListener(event -> {
            var fileChooser = new JFileChooser();
            fileChooser.showDialog(null, "Select MIDlet");

            SwingUtilities.invokeLater(() -> {
                if (MIDletLoader.getLastLoaded() == null) {
                    MIDletLoader.loadMIDlet(fileChooser.getSelectedFile().getAbsolutePath());
                    MIDletLoader.startLoadedMIDlet();
                }
                else {
                    HangarState.restartApp(fileChooser.getSelectedFile().getAbsolutePath());
                }
            });
        });
        restartMenuItem.addActionListener(event -> {
            HangarState.restartApp(MIDletLoader.getLastLoadedPath());
        });

        pauseMenuItem.addActionListener(event -> {
            MIDletLoader.getLastLoaded().pauseApp();
        });

        exitMenuItem.addActionListener(event -> System.exit(0));

        midletMenu.add(loadMenuItem);
        midletMenu.add(new JSeparator());
        midletMenu.add(pauseMenuItem);
        midletMenu.add(restartMenuItem);
        midletMenu.add(new JSeparator());
        midletMenu.add(exitMenuItem);
        this.add(midletMenu);
    }

    private void addOptionsMenu() {
        var optionsMenu = new JMenu("Options");

        var keyboardPopupMenu = new JMenu("Keyboard");
        var radioDefaultKeyboard = new JRadioButtonMenuItem("Default", HangarState.getKeyboard() == Keyboards.Default);
        var radioNokiaKeyboard = new JRadioButtonMenuItem("Nokia", HangarState.getKeyboard() == Keyboards.Nokia);

        var keyboardRadioGroup = new ButtonGroup();
        keyboardRadioGroup.add(radioDefaultKeyboard);
        keyboardRadioGroup.add(radioNokiaKeyboard);

        radioDefaultKeyboard.addItemListener(e -> {
            if (radioDefaultKeyboard.isSelected()) {
                HangarState.setKeyboard(Keyboards.Default);
            }
        });

        radioNokiaKeyboard.addItemListener(e -> {
            if (radioNokiaKeyboard.isSelected()) {
                HangarState.setKeyboard(Keyboards.Nokia);
            }
        });

        keyboardPopupMenu.add(radioDefaultKeyboard);
        keyboardPopupMenu.add(radioNokiaKeyboard);
        optionsMenu.add(keyboardPopupMenu);
        this.add(optionsMenu);
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