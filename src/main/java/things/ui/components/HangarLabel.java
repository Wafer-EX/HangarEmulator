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

import things.HangarState;
import things.enums.ScalingModes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HangarLabel extends JLabel {
    private static HangarLabel instance;

    private HangarLabel() {
        super();
        setPreferredSize(new Dimension(360, 360));
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        setText("Please select a file.");

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (HangarState.getScalingMode() == ScalingModes.ChangeResolution) {
                    HangarState.setResolution(e.getComponent().getSize());
                }
            }
        });
    }

    public static HangarLabel getInstance() {
        if (instance == null) {
            instance = new HangarLabel();
        }
        return instance;
    }
}