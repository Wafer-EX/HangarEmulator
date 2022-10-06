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

package things.ui.components;

import things.HangarAudio;
import things.HangarKeyCodes;
import things.HangarState;
import things.MIDletLoader;
import things.enums.ScalingModes;
import things.ui.dialogs.HangarJarChooser;
import things.ui.dialogs.HangarSf2Chooser;
import things.ui.frames.HangarMainFrame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class HangarMenuBar extends JMenuBar {
    public HangarMenuBar() {
        this.add(new HangarMIDletMenu());
        this.add(new HangarOptionsMenu());
        this.add(new HangarHelpMenu());
    }

    private static class HangarMIDletMenu extends JMenu {
        public HangarMIDletMenu() {
            super("MIDlet");
            var loadMenuItem = new JMenuItem("Load MIDlet");
            var restartMenuItem = new JMenuItem("Restart");
            var pauseMenuItem = new JMenuItem("Call pauseApp()");
            var exitMenuItem = new JMenuItem("Exit");

            loadMenuItem.addActionListener(e -> {
                var fileChooser = new HangarJarChooser();
                fileChooser.showDialog(null, "Select MIDlet");

                SwingUtilities.invokeLater(() -> {
                    var selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        if (!MIDletLoader.isLoaded()) {
                            MIDletLoader.loadMIDlet(fileChooser.getSelectedFile().getAbsolutePath());
                            MIDletLoader.startLoadedMIDlet();
                        }
                        else {
                            HangarState.restartApp(fileChooser.getSelectedFile().getAbsolutePath());
                        }
                    }
                });
            });

            restartMenuItem.addActionListener(e -> HangarState.restartApp(MIDletLoader.getLastLoadedPath()));

            pauseMenuItem.addActionListener(e -> {
                var lastLoaded = MIDletLoader.getLastLoaded();
                if (lastLoaded != null) {
                    lastLoaded.pauseApp();
                }
            });

            exitMenuItem.addActionListener(e -> System.exit(0));

            this.add(loadMenuItem);
            this.add(new JSeparator());
            this.add(pauseMenuItem);
            this.add(restartMenuItem);
            this.add(new JSeparator());
            this.add(exitMenuItem);
        }
    }

    private static class HangarOptionsMenu extends JMenu {
        public HangarOptionsMenu() {
            super("Options");
            var canvasClearingCheckBox = new JCheckBoxMenuItem("Canvas clearing");
            var antiAliasingCheckBox = new JCheckBoxMenuItem("Anti-aliasing");
            var frameRatePopupMenu = new JMenu("Frame rate");
            var scalingModePopupMenu = new JMenu("Scaling mode");
            var resolutionPopupMenu = new JMenu("Resolution");
            var loadSoundbankItem = new JMenuItem("Load soundbank");
            var clearSoundBankItem = new JMenuItem("Clear soundbank");
            var allowResizingCheckBox = new JCheckBoxMenuItem("Allow window resizing");
            var keyboardPopupMenu = new JMenu("Keyboard");

            canvasClearingCheckBox.setSelected(HangarState.getProfile().getCanvasClearing());
            canvasClearingCheckBox.addItemListener(e -> HangarState.getProfile().setCanvasClearing(!HangarState.getProfile().getCanvasClearing()));

            antiAliasingCheckBox.setSelected(HangarState.getProfile().getAntiAliasing());
            antiAliasingCheckBox.addItemListener(e -> HangarState.getProfile().setAntiAliasing(!HangarState.getProfile().getAntiAliasing()));

            frameRatePopupMenu.add(new HangarFrameRateRadio(15));
            frameRatePopupMenu.add(new HangarFrameRateRadio(30));
            frameRatePopupMenu.add(new HangarFrameRateRadio(60));
            frameRatePopupMenu.add(new HangarFrameRateRadio(-1));

            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.None, resolutionPopupMenu));
            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.Contain, resolutionPopupMenu));
            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.ChangeResolution, resolutionPopupMenu));

            resolutionPopupMenu.add(new HangarResolutionRadio(new Dimension(128, 128)));
            resolutionPopupMenu.add(new HangarResolutionRadio(new Dimension(128, 160)));
            resolutionPopupMenu.add(new HangarResolutionRadio(new Dimension(176, 220)));
            resolutionPopupMenu.add(new HangarResolutionRadio(new Dimension(240, 320)));

            loadSoundbankItem.addActionListener(e -> {
                var fileChooser = new HangarSf2Chooser();
                fileChooser.showDialog(null, "Select soundbank");

                SwingUtilities.invokeLater(() -> {
                    var selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        HangarAudio.loadSoundbank(selectedFile);
                    }
                });
            });

            clearSoundBankItem.addActionListener(e -> HangarAudio.setSoundbank(null));

            allowResizingCheckBox.setSelected(HangarState.getProfile().getWindowResizing());
            allowResizingCheckBox.addItemListener(e -> HangarState.getProfile().setWindowResizing(allowResizingCheckBox.getState()));

            keyboardPopupMenu.add(new HangarKeyboardRadio(HangarKeyCodes.MIDLET_KEYCODES_DEFAULT));
            keyboardPopupMenu.add(new HangarKeyboardRadio(HangarKeyCodes.MIDLET_KEYCODES_NOKIA));

            this.add(canvasClearingCheckBox);
            this.add(antiAliasingCheckBox);
            this.add(frameRatePopupMenu);
            this.add(scalingModePopupMenu);
            this.add(resolutionPopupMenu);
            this.add(new JSeparator());
            this.add(loadSoundbankItem);
            this.add(clearSoundBankItem);
            this.add(new JSeparator());
            this.add(allowResizingCheckBox);
            this.add(new JSeparator());
            this.add(keyboardPopupMenu);
        }

        private static class HangarFrameRateRadio extends JRadioButtonMenuItem {
            private static final ButtonGroup frameRateRadioGroup = new ButtonGroup();

            public HangarFrameRateRadio(int frameRate) {
                super();
                this.addItemListener(e -> HangarState.getProfile().setFrameRate(frameRate));
                this.setText(frameRate > -1 ? frameRate + " FPS" : "Unlimited");
                this.setSelected(HangarState.getProfile().getFrameRate() == frameRate);
                frameRateRadioGroup.add(this);
            }
        }

        private static class HangarScalingModeRadio extends JRadioButtonMenuItem {
            private static final ButtonGroup scalingModeRadioGroup = new ButtonGroup();

            public HangarScalingModeRadio(ScalingModes scalingMode, JMenu resolutionMenu) {
                super();
                this.setText(scalingMode.toString());
                this.setSelected(HangarState.getProfile().getScalingMode() == scalingMode);
                this.addItemListener(e -> {
                    HangarState.getProfile().setScalingMode(scalingMode);
                    resolutionMenu.setEnabled(scalingMode != ScalingModes.ChangeResolution);
                    if (scalingMode == ScalingModes.ChangeResolution) {
                        // TODO: resolutionRadioGroup.clearSelection();
                    }
                });
                scalingModeRadioGroup.add(this);
            }
        }

        private static class HangarResolutionRadio extends JRadioButtonMenuItem {
            private static final ButtonGroup resolutionRadioGroup = new ButtonGroup();

            public HangarResolutionRadio(Dimension resolution) {
                super();
                var profileResolution = HangarState.getProfile().getResolution();

                this.setText(resolution.width + "x" + resolution.height);
                this.setSelected(profileResolution.width == resolution.width && profileResolution.height == resolution.height);
                this.addItemListener(e -> {
                    if (this.isSelected()) {
                        HangarState.getProfile().setResolution(resolution);
                    }
                });
                resolutionRadioGroup.add(this);
            }
        }

        private static class HangarKeyboardRadio extends JRadioButtonMenuItem {
            private static final ButtonGroup keyboardRadioGroup = new ButtonGroup();

            public HangarKeyboardRadio(HangarKeyCodes keyCodes) {
                super();
                // TODO: rewrite text setting
                this.setText(keyCodes == HangarKeyCodes.MIDLET_KEYCODES_NOKIA ? "Nokia" : "Default");
                this.setSelected(HangarState.getProfile().getMidletKeyCodes() == keyCodes);
                this.addItemListener(e -> {
                    if (this.isSelected()) {
                        HangarState.getProfile().setMidletKeyCodes(keyCodes);
                    }
                });
                keyboardRadioGroup.add(this);
            }
        }
    }

    private static class HangarHelpMenu extends JMenu {
        public HangarHelpMenu() {
            super("Help");
            var githubLinkMenuItem = new JMenuItem("GitHub page");
            var showAboutMenuItem = new JMenuItem("About");

            githubLinkMenuItem.addActionListener(e -> {
                try {
                    var githubUri = new URL(System.getProperty("hangaremulator.github")).toURI();
                    Desktop.getDesktop().browse(githubUri);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            showAboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(
                    HangarMainFrame.getInstance(),
                    String.format("""
                            Hangar Emulator
                            Version: %s
                            Author: %s""",
                            System.getProperty("hangaremulator.version"),
                            System.getProperty("hangaremulator.author")),
                    "About",
                    JOptionPane.PLAIN_MESSAGE));

            this.add(githubLinkMenuItem);
            this.add(showAboutMenuItem);
        }
    }
}