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

import things.ui.frames.HangarMainFrame;
import things.ui.listeners.HangarKeyListener;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.swing.*;
import java.awt.*;

public class HangarViewport extends JPanel {
    // TODO: add displayable setting, replace main screen with setting another displayable
    private Displayable displayable = null;
    private HangarCanvasWrapper canvasWrapper = null;

    public HangarViewport() {
        super(new BorderLayout());

        var scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(360, 360));
    }

    public HangarCanvasWrapper getCanvasWrapper() {
        return canvasWrapper;
    }

    public Displayable getDisplayable() {
        return displayable;
    }

    public void setDisplayable(Displayable displayable) {
        this.removeAll();
        this.displayable = displayable;

        if (displayable.getCommands().size() > 0) {
            var displayableCommands = new HangarDisplayableCommands();
            this.add(displayableCommands, BorderLayout.SOUTH);
        }

        if (displayable instanceof Canvas canvas) {
            this.canvasWrapper = new HangarCanvasWrapper(canvas);
            this.add(canvasWrapper, BorderLayout.CENTER);

            var mainFrame = HangarMainFrame.getInstance();
            for (var keyListener : mainFrame.getKeyListeners()) {
                mainFrame.removeKeyListener(keyListener);
            }
            mainFrame.addKeyListener(new HangarKeyListener(canvas));
            mainFrame.setTitle(System.getProperty("MIDlet-Name"));
            mainFrame.requestFocus();

            SwingUtilities.invokeLater(canvas::showNotify);
        }
        else if (displayable instanceof List list) {
            var listWrapper = new HangarListWrapper(list);
            this.add(listWrapper, BorderLayout.CENTER);
        }
        else if (displayable instanceof Form form) {
            var formWrapper = new HangarFormWrapper(form);
            this.add(formWrapper, BorderLayout.CENTER);
        }
        else {
            // TODO: add another screens support
            throw new IllegalArgumentException();
        }

        this.revalidate();
        this.repaint();
    }

    private class HangarDisplayableCommands extends JScrollPane {
        public HangarDisplayableCommands() {
            super(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            var panel = new JPanel(new GridBagLayout());
            var commands = displayable.getCommands();
            var constraints = new GridBagConstraints();

            panel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 1.0;
            constraints.ipady = 12;
            constraints.insets.set(4, 2, 4, 2);

            for (int i = 0; i < commands.size(); i++) {
                var command = commands.get(i);
                var button = new JButton(commands.get(i).getLabel());

                button.addActionListener(e -> displayable.getCommandListener().commandAction(command, displayable));
                constraints.gridx = i;
                panel.add(button, constraints);
            }
            this.setViewportView(panel);
        }
    }
}