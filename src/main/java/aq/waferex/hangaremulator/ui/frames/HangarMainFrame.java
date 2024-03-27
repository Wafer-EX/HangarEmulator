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

package aq.waferex.hangaremulator.ui.frames;

import aq.waferex.hangaremulator.ui.listeners.events.HangarDisplayableEvent;
import aq.waferex.hangaremulator.ui.components.*;
import aq.waferex.hangaremulator.ui.listeners.HangarKeyListener;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("FieldCanBeLocal")
public class HangarMainFrame extends JFrame {
    // TODO: add more screens
    private final GridBagConstraints constraints = new GridBagConstraints();
    private final HangarMainPanel mainPanel = new HangarMainPanel();
    private final HangarViewport viewport = new HangarViewport();

    public HangarMainFrame() {
        super();

        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(360, 360));
        this.setTitle("Hangar Emulator");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());

        this.viewport.addDisplayableListener(e -> {
            if (e.getStateChange() == HangarDisplayableEvent.SET) {
                for (var keyListener : getKeyListeners()) {
                    removeKeyListener(keyListener);
                }

                var displayable = (Displayable) e.getSource();
                if (displayable instanceof Canvas canvas) {
                    addKeyListener(new HangarKeyListener(canvas));
                    requestFocus();
                }

                HangarMainFrame.this.setTitle(displayable.getTitle());
                mainPanel.setVisible(false);
                viewport.setVisible(true);
            }
        });
        viewport.setVisible(false);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        this.add(mainPanel, constraints);

        constraints.gridx = 1;
        this.add(viewport, constraints);

        this.pack();
        this.revalidate();
    }

    public HangarViewport getViewport() {
        return viewport;
    }
}