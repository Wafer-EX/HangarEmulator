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

package things.ui.frames;

import things.ui.listeners.events.HangarDisplayableEvent;
import things.ui.components.*;
import things.ui.listeners.HangarKeyListener;

import javax.microedition.lcdui.Canvas;
import javax.swing.*;
import java.awt.*;

public class HangarMainFrame extends JFrame {
    // TODO: add more screens
    private static final HangarMainFrame instance = new HangarMainFrame();
    private final GridBagConstraints constraints = new GridBagConstraints();
    private final HangarMainPanel mainPanel = new HangarMainPanel();
    private final HangarViewport displayableWrapper = new HangarViewport();

    private HangarMainFrame() {
        this.getContentPane().setLayout(new GridBagLayout());
        this.getContentPane().setPreferredSize(new Dimension(360, 360));
        this.setTitle("Hangar Emulator");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());

        this.displayableWrapper.addDisplayableListener(e -> {
            if (e.getStateChange() == HangarDisplayableEvent.SET) {
                for (var keyListener : getKeyListeners()) {
                    removeKeyListener(keyListener);
                }
                if (e.getSource() instanceof Canvas canvas) {
                    addKeyListener(new HangarKeyListener(canvas));
                    setTitle(System.getProperty("MIDlet-Name"));
                    requestFocus();
                }

                mainPanel.setVisible(false);
                displayableWrapper.setVisible(true);
            }
        });
        displayableWrapper.setVisible(false);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        this.add(mainPanel, constraints);

        constraints.gridx = 1;
        this.add(displayableWrapper, constraints);

        this.pack();
        this.revalidate();
    }

    public static HangarMainFrame getInstance() {
        return instance;
    }

    public HangarViewport getDisplayableWrapper() {
        return displayableWrapper;
    }
}