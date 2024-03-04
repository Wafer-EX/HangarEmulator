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

package things.ui.components;

import things.HangarState;
import things.enums.ScalingModes;
import things.ui.listeners.events.HangarProfileEvent;

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
        var profile = HangarState.getProfileManager().getCurrentProfile();
        profile.addProfileListener(e -> {
            if (e.getStateChange() == HangarProfileEvent.SCALING_MODE_CHANGED) {
                if (profile.getScalingMode() == ScalingModes.ChangeResolution) {
                    profile.setResolution(getSize());
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var profile = HangarState.getProfileManager().getCurrentProfile();
                if (profile.getScalingMode() == ScalingModes.ChangeResolution) {
                    profile.setResolution(getSize());
                }
            }
        });
    }
}