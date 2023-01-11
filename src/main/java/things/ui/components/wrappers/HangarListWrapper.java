/*
 * Copyright 2022-2023 Kirill Lomakin
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

package things.ui.components.wrappers;

import javax.microedition.lcdui.List;
import javax.swing.*;
import java.awt.*;

public class HangarListWrapper extends JPanel {
    public HangarListWrapper(List list) {
        super(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.ipady = 24;

        for (int i = 0; i < list.size(); i++) {
            int selectedIndex = i;
            var button = new JButton(list.getString(i));

            button.addActionListener(e -> {
                list.setSelectedIndex(selectedIndex, true);
                list.runSelectCommand();
            });

            constraints.insets.set(i == 0 ? 4 : 2, 4, i == list.size() - 1 ? 4 : 2, 4);
            constraints.gridy = i;
            this.add(button, constraints);
        }

        constraints.insets.set(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = list.size();
        constraints.weighty = 1.0;
        constraints.ipady = 0;
        this.add(Box.createGlue(), constraints);
    }
}