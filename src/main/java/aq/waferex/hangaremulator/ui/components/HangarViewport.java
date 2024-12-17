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

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.ui.components.wrappers.*;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.List;
import javax.swing.*;
import java.awt.*;

public class HangarViewport extends JPanel {
    public HangarViewport() {
        super(new BorderLayout());
    }

    public void displayableHasChanged(Displayable displayable) {
        if (displayable == null) {
            throw new NullPointerException();
        }

        this.removeAll();

        // Add scroll pane for wrapper
        var scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        // Add button command at bottom
        if (!displayable.getCommands().isEmpty()) {
            var displayableCommands = new HangarViewportCommands(displayable);
            this.add(displayableCommands, BorderLayout.SOUTH);
        }

        // Add title at top
        if (displayable.getTitle() != null && !(displayable instanceof Canvas canvas && canvas.isFullScreenEnabled())) {
            var label = new JLabel(displayable.getTitle(), JLabel.CENTER);
            this.add(label, BorderLayout.NORTH);
        }

        // Displayable is always not null here
        if (displayable instanceof Canvas canvas) {
            var canvasWrapper = new HangarCanvasWrapper(canvas);
            canvas.setRelatedWrapper(canvasWrapper);
            scrollPane.setViewportView(canvasWrapper);

            SwingUtilities.invokeLater(canvas::showNotify);
            HangarState.getMainFrame().requestFocus();
        }
        else if (displayable instanceof List list) {
            var listWrapper = new HangarListWrapper(list);
            list.setRelatedWrapper(listWrapper);
            scrollPane.setViewportView(listWrapper);
        }
        else if (displayable instanceof Form form) {
            var formWrapper = new HangarFormWrapper(form);
            form.setRelatedWrapper(formWrapper);
            scrollPane.setViewportView(formWrapper);
        }
        else if (displayable instanceof TextBox textBox) {
            var textBoxWrapper = new HangarTextBoxWrapper(textBox);
            textBox.setRelatedWrapper(textBoxWrapper);
            scrollPane.setViewportView(textBoxWrapper);
        }
        // TODO: add more screens support
        else {
            throw new IllegalArgumentException();
        }

        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    private static class HangarViewportCommands extends JScrollPane {
        public HangarViewportCommands(Displayable displayable) {
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