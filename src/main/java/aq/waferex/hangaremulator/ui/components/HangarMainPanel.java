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
import aq.waferex.hangaremulator.enums.ScalingModes;
import aq.waferex.hangaremulator.utils.SystemUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HangarMainPanel extends JPanel {
    // TODO: replace with profiles menu
    public HangarMainPanel() {
        super(new CardLayout());

        var label = new JLabel("Please select a file in the MIDlet menu.");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        this.add(label);

        // TODO: the scale can not match with real viewport because of buttons in bottom
        // TODO: remove?
//        var profile = HangarState.getProfileManager().getCurrentProfile();
//        profile.addProfileListener(e -> {
//            if (e.getStateChange() == HangarProfileEvent.SCALING_MODE_CHANGED) {
//                if (profile.getScalingMode() == ScalingModes.ChangeResolution) {
//                    profile.setResolution(getSize());
//                }
//            }
//        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var graphicsSettings = HangarState.getGraphicsSettings();
                if (graphicsSettings.getScalingMode() == ScalingModes.ChangeResolution) {
                    float scalingInUnits = SystemUtils.getScalingInUnits();
                    int realWidth = (int) (getWidth() * scalingInUnits);
                    int realHeight = (int) (getHeight() * scalingInUnits);
                    graphicsSettings.setResolution(new Dimension(realWidth, realHeight));
                }
            }
        });
    }
}