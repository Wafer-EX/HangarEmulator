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

import javax.microedition.lcdui.Displayable;
import javax.swing.*;
import java.awt.*;

public class HangarDisplayable extends JPanel {
    public HangarDisplayable(JPanel screen, Displayable displayable) {
        super(new GridBagLayout());
        var constraints = new GridBagConstraints();
        var scrollPane = new JScrollPane(screen, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        var commands = displayable.getCommands();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridwidth = commands.size();
        this.add(scrollPane, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.ipady = 12;

        for (int i = 0; i < commands.size(); i++) {
            var command = commands.get(i);
            var button = new JButton(commands.get(i).getLabel());
            button.addActionListener(e -> displayable.getCommandListener().commandAction(command, displayable));

            constraints.insets.set(4, i == commands.size() - 1 && commands.size() > 2 ? 0 : 4, 4, i == 0 && commands.size() > 1 ? 0 : 4);
            constraints.gridx = i;
            this.add(button, constraints);
        }
        this.revalidate();
    }
}