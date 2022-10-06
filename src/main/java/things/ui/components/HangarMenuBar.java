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

    @SuppressWarnings("FieldCanBeLocal")
    private static class HangarMIDletMenu extends JMenu {
        private final JMenuItem loadMenuItem = new JMenuItem("Load MIDlet");
        private final JMenuItem restartMenuItem = new JMenuItem("Restart");
        private final JMenuItem pauseMenuItem = new JMenuItem("Call pauseApp()");
        private final JMenuItem exitMenuItem = new JMenuItem("Exit");

        public HangarMIDletMenu() {
            super("MIDlet");
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

    @SuppressWarnings("FieldCanBeLocal")
    private static class HangarOptionsMenu extends JMenu {
        private final ButtonGroup frameRateRadioGroup = new ButtonGroup();
        private final ButtonGroup scalingModeRadioGroup = new ButtonGroup();
        private final ButtonGroup resolutionRadioGroup = new ButtonGroup();

        private final JCheckBoxMenuItem canvasClearingCheckBox = new JCheckBoxMenuItem("Canvas clearing");
        private final JCheckBoxMenuItem antiAliasingCheckBox = new JCheckBoxMenuItem("Anti-aliasing");
        private final JMenu frameRatePopupMenu = new JMenu("Frame rate");
        private final JMenu scalingModePopupMenu = new JMenu("Scaling mode");
        private final JMenu resolutionPopupMenu = new JMenu("Resolution");
        private final JMenuItem loadSoundbankItem = new JMenuItem("Load soundbank");
        private final JMenuItem clearSoundBankItem = new JMenuItem("Clear soundbank");
        private final JCheckBoxMenuItem allowResizingCheckBox = new JCheckBoxMenuItem("Allow window resizing");
        private final JMenu keyboardPopupMenu = new JMenu("Keyboard");

        public HangarOptionsMenu() {
            super("Options");
            canvasClearingCheckBox.setSelected(HangarState.getProfile().getCanvasClearing());
            canvasClearingCheckBox.addActionListener(e -> HangarState.getProfile().setCanvasClearing(!HangarState.getProfile().getCanvasClearing()));

            antiAliasingCheckBox.setSelected(HangarState.getProfile().getAntiAliasing());
            antiAliasingCheckBox.addActionListener(e -> HangarState.getProfile().setAntiAliasing(!HangarState.getProfile().getAntiAliasing()));

            frameRatePopupMenu.add(new HangarFrameRateRadio(15));
            frameRatePopupMenu.add(new HangarFrameRateRadio(30));
            frameRatePopupMenu.add(new HangarFrameRateRadio(60));
            frameRatePopupMenu.add(new HangarFrameRateRadio(-1));

            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.None));
            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.Contain));
            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.ChangeResolution));

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
            allowResizingCheckBox.addActionListener(e -> HangarState.getProfile().setWindowResizing(allowResizingCheckBox.getState()));

            keyboardPopupMenu.add(new HangarKeyboardRadio("Default", HangarKeyCodes.MIDLET_KEYCODES_DEFAULT));
            keyboardPopupMenu.add(new HangarKeyboardRadio("Nokia", HangarKeyCodes.MIDLET_KEYCODES_NOKIA));

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

        private class HangarFrameRateRadio extends JRadioButtonMenuItem {
            public HangarFrameRateRadio(int frameRate) {
                super();
                this.addItemListener(e -> HangarState.getProfile().setFrameRate(frameRate));
                this.setText(frameRate > -1 ? frameRate + " FPS" : "Unlimited");
                this.setSelected(HangarState.getProfile().getFrameRate() == frameRate);
                frameRateRadioGroup.add(this);
            }
        }

        private class HangarScalingModeRadio extends JRadioButtonMenuItem {
            public HangarScalingModeRadio(ScalingModes scalingMode) {
                super();
                this.setText(scalingMode.toString());
                this.setSelected(HangarState.getProfile().getScalingMode() == scalingMode);
                this.addItemListener(e -> {
                    HangarState.getProfile().setScalingMode(scalingMode);
                    resolutionPopupMenu.setEnabled(scalingMode != ScalingModes.ChangeResolution);
                    if (scalingMode == ScalingModes.ChangeResolution) {
                        resolutionRadioGroup.clearSelection();
                    }
                });
                scalingModeRadioGroup.add(this);
            }
        }

        private class HangarResolutionRadio extends JRadioButtonMenuItem {
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

            public HangarKeyboardRadio(String keyboardName, HangarKeyCodes keyCodes) {
                super();
                // TODO: rewrite text setting
                this.setText(keyboardName);
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

    @SuppressWarnings("FieldCanBeLocal")
    private static class HangarHelpMenu extends JMenu {
        private final JMenuItem githubLinkMenuItem = new JMenuItem("GitHub page");
        private final JMenuItem showAboutMenuItem = new JMenuItem("About");

        public HangarHelpMenu() {
            super("Help");
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