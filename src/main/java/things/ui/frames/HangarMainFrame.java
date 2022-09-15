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

package things.ui.frames;

import things.ui.components.HangarMainPanel;
import things.ui.components.HangarMenuBar;
import things.ui.components.HangarGamePanel;
import things.ui.listeners.HangarKeyListener;

import javax.swing.*;
import java.awt.*;

public class HangarMainFrame extends JFrame {
    private static final HangarMainFrame instance = new HangarMainFrame();
    private static final Dimension defaultSize = new Dimension(360, 360);
    private HangarGamePanel gamePanel = null;

    private HangarMainFrame() {
        this.setTitle("Hangar Emulator");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());
    }

    public static HangarMainFrame getInstance() {
        return instance;
    }

    public HangarGamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(HangarGamePanel gamePanel) {
        var size = defaultSize;
        var components = getContentPane().getComponents();

        for (var component : components) {
            if (component instanceof HangarMainPanel mainPanel) {
                size = mainPanel.getSize();
                remove(component);
            }
        }
        gamePanel.setPreferredSize(size);

        for (var keyListener : getKeyListeners()) {
            removeKeyListener(keyListener);
        }
        this.addKeyListener(new HangarKeyListener(gamePanel));

        super.add(gamePanel);
        this.pack();
        this.revalidate();
        this.gamePanel = gamePanel;
    }
}