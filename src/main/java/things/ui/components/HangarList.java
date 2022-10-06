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

import javax.microedition.lcdui.List;
import javax.swing.*;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HangarList extends JPanel {
    private final List meList;

    public HangarList(List meList) {
        super(new GridBagLayout());
        this.meList = meList;

        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridwidth = meList.size();
        this.add(new JScrollPane(new HangarListPanel()), constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.ipady = 12;

        var commands = meList.getCommands();
        for (int i = 0; i < commands.size(); i++) {
            var command = commands.get(i);
            var button = new JButton(new TextAction(commands.get(i).getLabel()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    meList.getCommandListener().commandAction(command, meList);
                }
            });

            constraints.insets.set(4, i == commands.size() - 1 && commands.size() > 1 ? 0 : 4, 4, i == 0 && commands.size() > 1 ? 0 : 4);
            constraints.gridx = i;
            this.add(button, constraints);
        }
        this.revalidate();
    }

    private class HangarListPanel extends JPanel {
        public HangarListPanel() {
            super(new GridBagLayout());

            var constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 1.0;
            constraints.ipady = 24;

            for (int i = 0; i < meList.size(); i++) {
                int selectedIndex = i;
                var button = new JButton(new TextAction(meList.getString(i)) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        meList.setSelectedIndex(selectedIndex, true);
                        meList.runSelectCommand();
                    }
                });

                constraints.insets.set(i == 0 ? 4 : 2, 4, i == meList.size() - 1 ? 4 : 2, 4);
                constraints.gridy = i;
                this.add(button, constraints);
            }

            constraints.insets.set(0, 0, 0, 0);
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridy = meList.size();
            constraints.weighty = 1.0;
            constraints.ipady = 0;

            this.add(Box.createGlue(), constraints);
        }
    }
}