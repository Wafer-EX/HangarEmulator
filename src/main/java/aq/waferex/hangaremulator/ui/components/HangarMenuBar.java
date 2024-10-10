/*
 * Copyright 2022-2024 Wafer EX
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

package aq.waferex.hangaremulator.ui.components;

import aq.waferex.hangaremulator.MIDletLoader;
import aq.waferex.hangaremulator.ui.dialogs.HangarFileChooser;
import aq.waferex.hangaremulator.utils.AudioUtils;
import aq.waferex.hangaremulator.HangarKeyCodes;
import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.enums.ScalingModes;
import aq.waferex.hangaremulator.utils.SystemUtils;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class HangarMenuBar extends JMenuBar {
    public HangarMenuBar() {
        this.add(new HangarMIDletMenu());
        this.add(new HangarOptionsMenu());
        this.add(new HangarHelpMenu());
    }

    @SuppressWarnings("FieldCanBeLocal")
    private static class HangarMIDletMenu extends JMenu {
        private final JMenuItem loadMenuItem = new JMenuItem("Load MIDlet");
        private final JMenuItem pauseMenuItem = new JMenuItem("Call pauseApp()");
        private final JCheckBoxMenuItem systemLanguageCheckbox = new JCheckBoxMenuItem("Use system language");
        private final JMenuItem restartMenuItem = new JMenuItem("Restart");
        private final JMenuItem exitMenuItem = new JMenuItem("Exit");

        public HangarMIDletMenu() {
            super("MIDlet");
            loadMenuItem.addActionListener(e -> {
                var fileChooser = new HangarFileChooser(new String[] { "jar" }, "MIDlet (*.jar)");
                fileChooser.showDialog(null, "Select MIDlet");

                SwingUtilities.invokeLater(() -> {
                    var selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        if (HangarState.getMIDletLoader() == null) {
                            var midletLoader = new MIDletLoader(fileChooser.getSelectedFile().getAbsolutePath());
                            HangarState.setMIDletLoader(midletLoader);
                            midletLoader.startMIDlet();
                        }
                        else {
                            SystemUtils.restartApp(fileChooser.getSelectedFile().getAbsolutePath());
                        }
                    }
                });
            });

            pauseMenuItem.addActionListener(e -> {
                var currentMidlet = HangarState.getMIDletLoader().getMIDlet();
                if (currentMidlet != null) {
                    currentMidlet.pauseApp();
                }
            });

            systemLanguageCheckbox.addActionListener(e -> {
                var property = systemLanguageCheckbox.getState() ? Locale.getDefault().toLanguageTag() : "en-US";
                HangarState.getProperties().setProperty("microedition.locale", property);
            });

            restartMenuItem.addActionListener(e ->  {
                var currentMidlet = HangarState.getMIDletLoader();
                if (currentMidlet != null) {
                    var midletPath = currentMidlet.getMIDletPath();
                    SystemUtils.restartApp(midletPath);
                }
                else {
                    SystemUtils.restartApp(null);
                }
            });

            exitMenuItem.addActionListener(e -> System.exit(0));

            this.add(loadMenuItem);
            this.add(new JSeparator());
            this.add(pauseMenuItem);
            this.add(systemLanguageCheckbox);
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

        private final JCheckBoxMenuItem screenClearingCheckBox = new JCheckBoxMenuItem("Apply screen clearing");
        private final JCheckBoxMenuItem vectorAntiAliasingCheckBox = new JCheckBoxMenuItem("Apply vector anti-aliasing");
        private final JMenu frameRatePopupMenu = new JMenu("Frame rate");
        private final JMenu scalingModePopupMenu = new JMenu("Scaling mode");
        private final JMenu resolutionPopupMenu = new JMenu("Resolution");
        private final JMenuItem loadSoundbankItem = new JMenuItem("Load soundbank");
        private final JMenuItem clearSoundBankItem = new JMenuItem("Clear soundbank");
        private final JCheckBoxMenuItem allowResizingCheckBox = new JCheckBoxMenuItem("Allow window resizing");
        private final JMenu keyboardPopupMenu = new JMenu("Keyboard");

        public HangarOptionsMenu() {
            super("Options");
            screenClearingCheckBox.setSelected(HangarState.getGraphicsSettings().getScreenClearing());
            screenClearingCheckBox.addActionListener(e -> HangarState.getGraphicsSettings().setScreenClearing(!HangarState.getGraphicsSettings().getScreenClearing()));

            vectorAntiAliasingCheckBox.setSelected(HangarState.getGraphicsSettings().getVectorAntiAliasing());
            vectorAntiAliasingCheckBox.addActionListener(e -> HangarState.getGraphicsSettings().setVectorAntiAliasing(!HangarState.getGraphicsSettings().getVectorAntiAliasing()));

            frameRatePopupMenu.add(new HangarFrameRateRadio(15));
            frameRatePopupMenu.add(new HangarFrameRateRadio(30));
            frameRatePopupMenu.add(new HangarFrameRateRadio(60));
            frameRatePopupMenu.add(new HangarFrameRateRadio(-1));

            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.Percent));
            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.Fit));
            scalingModePopupMenu.add(new HangarScalingModeRadio(ScalingModes.ChangeResolution));

            resolutionPopupMenu.add(new HangarResolutionRadio(new Dimension(128, 128)));
            resolutionPopupMenu.add(new HangarResolutionRadio(new Dimension(128, 160)));
            resolutionPopupMenu.add(new HangarResolutionRadio(new Dimension(176, 220)));
            resolutionPopupMenu.add(new HangarResolutionRadio(new Dimension(240, 320)));

            loadSoundbankItem.addActionListener(e -> {
                var fileChooser = new HangarFileChooser(new String[] { "sf2" }, "Soundbank (*.sf2)");
                fileChooser.showDialog(null, "Select soundbank");

                SwingUtilities.invokeLater(() -> {
                    var selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        try {
                            HangarState.getAudioSettings().setSoundbankFile(selectedFile);
                        }
                        catch (IOException | InvalidMidiDataException exception) {
                            exception.printStackTrace();
                            JOptionPane.showMessageDialog(HangarState.getMainFrame(),
                                    "The file format is invalid.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            });

            clearSoundBankItem.addActionListener(e -> AudioUtils.setSoundbank(null));

            allowResizingCheckBox.setSelected(HangarState.getWindowSettings().getWindowResizing());
            allowResizingCheckBox.addActionListener(e -> HangarState.getWindowSettings().setWindowResizing(allowResizingCheckBox.getState()));

            keyboardPopupMenu.add(new HangarKeyboardRadio("Default", HangarKeyCodes.MIDLET_KEYCODES_DEFAULT));
            keyboardPopupMenu.add(new HangarKeyboardRadio("Nokia", HangarKeyCodes.MIDLET_KEYCODES_NOKIA));

            this.add(screenClearingCheckBox);
            this.add(vectorAntiAliasingCheckBox);
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
                this.addItemListener(e -> HangarState.getGraphicsSettings().setFrameRate(frameRate));
                this.setText(frameRate > -1 ? frameRate + " FPS" : "Unlimited");
                this.setSelected(HangarState.getGraphicsSettings().getFrameRate() == frameRate);
                frameRateRadioGroup.add(this);
            }
        }

        private class HangarScalingModeRadio extends JRadioButtonMenuItem {
            public HangarScalingModeRadio(ScalingModes scalingMode) {
                super();
                this.setText(scalingMode.toString());
                this.setSelected(HangarState.getGraphicsSettings().getScalingMode() == scalingMode);
                this.addItemListener(e -> {
                    HangarState.getGraphicsSettings().setScalingMode(scalingMode);
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
                var profileResolution = HangarState.getGraphicsSettings().getResolution();

                this.setText(resolution.width + "x" + resolution.height);
                this.setSelected(profileResolution.width == resolution.width && profileResolution.height == resolution.height);
                this.addItemListener(e -> {
                    if (this.isSelected()) {
                        HangarState.getGraphicsSettings().setResolution(resolution);
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
                this.setSelected(HangarState.getKeyboardSettings().getMidletKeyCodes() == keyCodes);
                this.addItemListener(e -> {
                    if (this.isSelected()) {
                        HangarState.getKeyboardSettings().setMidletKeyCodes(keyCodes);
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
                    var githubUri = new URL(HangarState.getProperties().getProperty("hangaremulator.github")).toURI();
                    Desktop.getDesktop().browse(githubUri);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            showAboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(
                    HangarState.getMainFrame(),
                    String.format("""
                            Hangar Emulator
                            Version: %s
                            Author: %s""",
                            HangarState.getProperties().getProperty("hangaremulator.version"),
                            HangarState.getProperties().getProperty("hangaremulator.author")),
                    "About",
                    JOptionPane.PLAIN_MESSAGE));

            this.add(githubLinkMenuItem);
            this.add(showAboutMenuItem);
        }
    }
}