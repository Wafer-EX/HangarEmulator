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

package aq.waferex.hangaremulator.ui.components.wrappers;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.StringItem;
import javax.swing.*;
import java.awt.*;

public class HangarFormWrapper extends JPanel {
    public HangarFormWrapper(Form form) {
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
                // TODO: check it
                var imageIcon = new ImageIcon(imageItem.getImage().getBufferedImage());
                var label = new JLabel(imageIcon);
                this.add(label, constraints);
            }
            else {
                // TODO: add another items
                throw new IllegalArgumentException();
            }
            constraints.gridy += 1;
        }

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;
        this.add(Box.createGlue(), constraints);
    }
}