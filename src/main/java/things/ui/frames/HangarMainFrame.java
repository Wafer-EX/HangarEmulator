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

import things.ui.components.*;
import things.ui.listeners.HangarKeyListener;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.swing.*;
import java.awt.*;

public class HangarMainFrame extends JFrame {
    private static final HangarMainFrame instance = new HangarMainFrame();
    private static final Dimension defaultSize = new Dimension(360, 360);
    private Displayable displayable;
    private HangarCanvas gamePanel = null;

    private HangarMainFrame() {
        this.getContentPane().setLayout(new CardLayout());
        this.setTitle("Hangar Emulator");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());
    }

    public static HangarMainFrame getInstance() {
        return instance;
    }

    public HangarCanvas getGamePanel() {
        return gamePanel;
    }

    public Displayable getDisplayable() {
        return displayable;
    }

    public void setDisplayable(Displayable displayable) {
        this.getContentPane().removeAll();
        this.displayable = displayable;

        if (displayable instanceof Canvas canvas) {
            if (gamePanel == null) {
                gamePanel = new HangarCanvas();
            }

            for (var keyListener : getKeyListeners()) {
                removeKeyListener(keyListener);
            }
            this.addKeyListener(new HangarKeyListener(displayable));

            this.setTitle(System.getProperty("MIDlet-Name"));
            this.requestFocus();
            this.getContentPane().add(gamePanel);

            gamePanel.setDisplayable(canvas);
            gamePanel.updateBufferTransformations();
            SwingUtilities.invokeLater(canvas::showNotify);
        }
        else if (displayable instanceof List list) {
            this.getContentPane().add(new HangarDisplayable(new HangarList(list), list));
        }
        else if (displayable instanceof Form form) {
            this.getContentPane().add(new HangarDisplayable(new HangarForm(form), form));
        }
        else {
            // TODO: add another screens support
            throw new IllegalArgumentException();
        }

        this.revalidate();
        this.repaint();
    }
}