/*
 * Copyright 2022 Kirill Lomakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
            var selectedFile = fileChooser.getSelectedFile();

            SwingUtilities.invokeLater(() -> {
                if (selectedFile != null) {
                    if (MIDletLoader.getLastLoaded() == null) {
                        MIDletLoader.loadMIDlet(fileChooser.getSelectedFile().getAbsolutePath());
                        MIDletLoader.startLoadedMIDlet();
                    }
                    else {
                        HangarState.restartApp(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                }
            });
        });
        restartMenuItem.addActionListener(event -> HangarState.restartApp(MIDletLoader.getLastLoadedPath()));
        pauseMenuItem.addActionListener(event -> MIDletLoader.getLastLoaded().pauseApp());
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
        var canvasClearingCheckBox = new JCheckBoxMenuItem("Canvas clearing", HangarState.getCanvasClearing());

        var radioDefaultKeyboard = new JRadioButtonMenuItem("Default", HangarState.getKeyboard() == Keyboards.Default);
        var radioNokiaKeyboard = new JRadioButtonMenuItem("Nokia", HangarState.getKeyboard() == Keyboards.Nokia);

        var keyboardRadioGroup = new ButtonGroup();
        keyboardRadioGroup.add(radioDefaultKeyboard);
        keyboardRadioGroup.add(radioNokiaKeyboard);

        canvasClearingCheckBox.addItemListener(e -> {
            var clearCanvas = !HangarState.getCanvasClearing();
            HangarState.setCanvasClearing(clearCanvas);
        });

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
        optionsMenu.add(canvasClearingCheckBox);
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