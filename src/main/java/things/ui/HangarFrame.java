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

import things.ui.components.HangarMainPanel;
import things.ui.components.HangarMenuBar;
import things.ui.components.HangarGamePanel;
import things.ui.input.HangarKeyListener;

import javax.swing.*;
import java.awt.*;

public class HangarFrame extends JFrame {
    private static final HangarFrame instance = new HangarFrame();
    private static final Dimension defaultSize = new Dimension(360, 360);
    private HangarGamePanel hangarGamePanel = null;

    private HangarFrame() {
        this.setTitle("Hangar Emulator");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());
    }

    public static HangarFrame getInstance() {
        return instance;
    }

    public HangarGamePanel getHangarGamePanel() {
        return hangarGamePanel;
    }

    public void setHangarGamePanel(HangarGamePanel hangarGamePanel) {
        var size = defaultSize;
        var components = getContentPane().getComponents();

        for (var component : components) {
            if (component instanceof HangarMainPanel hangarMainPanel) {
                size = hangarMainPanel.getSize();
                remove(component);
            }
        }
        hangarGamePanel.setPreferredSize(size);

        for (var keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }
        addKeyListener(new HangarKeyListener(hangarGamePanel));

        super.add(hangarGamePanel);
        pack();
        revalidate();
        this.hangarGamePanel = hangarGamePanel;
    }
}