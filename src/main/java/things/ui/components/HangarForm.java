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

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.StringItem;
import javax.swing.*;
import java.awt.*;

public class HangarForm extends JPanel {
    public HangarForm(Form form) {
        super(new GridBagLayout());

        var items = form.getItems();
        var constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridy = 0;

        for (var item : items) {
            if (item instanceof StringItem stringItem) {
                var label = new JLabel(stringItem.getText());
                this.add(label, constraints);
            }
            else if (item instanceof ImageItem imageItem) {
                var imageIcon = new ImageIcon(imageItem.getImage().getSEImage());
                var label = new JLabel(imageIcon);
                this.add(label, constraints);
            }
            else {
                throw new IllegalArgumentException();
            }
            constraints.gridy += 1;
        }

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;
        this.add(Box.createGlue(), constraints);
    }
}