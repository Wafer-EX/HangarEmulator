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
        var frameRatePopupMenu = new JMenu("Frame rate");
        var canvasClearingCheckBox = new JCheckBoxMenuItem("Canvas clearing", HangarState.getCanvasClearing());

        var radioDefaultKeyboard = new JRadioButtonMenuItem("Default", HangarState.getKeyboard() == Keyboards.Default);
        var radioNokiaKeyboard = new JRadioButtonMenuItem("Nokia", HangarState.getKeyboard() == Keyboards.Nokia);
        var keyboardRadioGroup = new ButtonGroup();
        keyboardRadioGroup.add(radioDefaultKeyboard);
        keyboardRadioGroup.add(radioNokiaKeyboard);

        var radio15FPS = new JRadioButtonMenuItem("15 FPS", HangarState.getFrameRate() == 15);
        var radio30FPS = new JRadioButtonMenuItem("30 FPS", HangarState.getFrameRate() == 30);
        var radio60FPS = new JRadioButtonMenuItem("60 FPS", HangarState.getFrameRate() == 60);
        var radioUnlimitedFPS = new JRadioButtonMenuItem("Unlimited FPS", HangarState.getFrameRate() == -1);
        var frameRateRadioGroup = new ButtonGroup();
        frameRateRadioGroup.add(radio15FPS);
        frameRateRadioGroup.add(radio30FPS);
        frameRateRadioGroup.add(radio60FPS);
        frameRateRadioGroup.add(radioUnlimitedFPS);

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

        radio15FPS.addItemListener(e -> HangarState.setFrameRate(15));
        radio30FPS.addItemListener(e -> HangarState.setFrameRate(30));
        radio60FPS.addItemListener(e -> HangarState.setFrameRate(60));
        radioUnlimitedFPS.addItemListener(e -> HangarState.setFrameRate(-1));

        keyboardPopupMenu.add(radioDefaultKeyboard);
        keyboardPopupMenu.add(radioNokiaKeyboard);
        frameRatePopupMenu.add(radio15FPS);
        frameRatePopupMenu.add(radio30FPS);
        frameRatePopupMenu.add(radio60FPS);
        frameRatePopupMenu.add(radioUnlimitedFPS);

        optionsMenu.add(canvasClearingCheckBox);
        optionsMenu.add(frameRatePopupMenu);
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