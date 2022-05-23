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

package things;

import javax.swing.*;
import java.awt.*;

public class HangarFrame extends JFrame {
    private static HangarFrame instance;
    private JLabel currentLabel;

    private HangarFrame() {
        this.setTitle("Hangar Emulator");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());
    }

    public static HangarFrame getInstance() {
        if (instance == null) {
            instance = new HangarFrame();
        }
        return instance;
    }

    public JLabel getLabel() {
        return currentLabel;
    }

    public void setLabel(HangarLabel label) {
        label.setPreferredSize(new Dimension(240, 320));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        this.add(label);
        this.pack();
        this.revalidate();
        currentLabel = label;
    }

    public void setHangarPanel() {
        if (getLabel() != null) {
            this.remove(getLabel());
        }
        this.setJMenuBar(new HangarMenuBar());
        this.add(HangarPanel.getInstance());
        this.addKeyListener(new HangarKeyListener());
        this.pack();
        this.revalidate();
    }
}