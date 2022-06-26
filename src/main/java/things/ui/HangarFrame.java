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

package things.ui;

import things.ui.input.HangarKeyListener;
import things.ui.components.HangarLabel;
import things.ui.components.HangarMenuBar;
import things.ui.components.HangarPanel;

import javax.swing.*;
import java.awt.*;

public class HangarFrame extends JFrame {
    private static final HangarFrame instance = new HangarFrame();
    private static final Dimension defaultHangarSize = new Dimension(360, 360);
    private HangarPanel hangarPanel;
    private HangarLabel hangarLabel;

    private HangarFrame() {
        this.setTitle("Hangar Emulator");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());
    }

    public static HangarFrame getInstance() {
        return instance;
    }

    public Component add(HangarLabel hangarLabel) {
        hangarLabel.setPreferredSize(hangarPanel != null ? hangarPanel.getSize() : defaultHangarSize);
        super.add(hangarLabel);
        for (var keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }

        pack();
        revalidate();
        return this.hangarLabel = hangarLabel;
    }

    public Component add(HangarPanel hangarPanel) {
        hangarPanel.setPreferredSize(hangarLabel != null ? hangarLabel.getSize() : defaultHangarSize);
        super.add(hangarPanel);
        if (hangarLabel != null) {
            remove(hangarLabel);
        }

        addKeyListener(new HangarKeyListener(hangarPanel));
        pack();
        revalidate();
        return this.hangarPanel = hangarPanel;
    }

    public HangarLabel getHangarLabel() {
        return hangarLabel;
    }

    public HangarPanel getHangarPanel() {
        return hangarPanel;
    }
}