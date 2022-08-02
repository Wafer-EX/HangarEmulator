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

package things.ui.listeners;

import things.ui.components.HangarGamePanel;
import things.HangarState;
import things.enums.Keyboards;
import things.utils.KeyUtils;

import javax.microedition.lcdui.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class HangarKeyListener implements KeyListener {
    private final HangarGamePanel gamePanel;
    private final HashMap<Integer, Boolean> pressedKeys = new HashMap<>();

    public HangarKeyListener(HangarGamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public HashMap<Integer, Boolean> getPressedKeys() {
        return pressedKeys;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gamePanel.getDisplayable() instanceof Canvas canvas) {
            int convertedKeyCode = KeyUtils.awtToDefault(e.getKeyCode());
            if (HangarState.getKeyboard() == Keyboards.Nokia) {
                convertedKeyCode = KeyUtils.defaultToNokia(convertedKeyCode);
            }

            if (pressedKeys.containsKey(convertedKeyCode)) {
                if (!pressedKeys.get(convertedKeyCode)) {
                    pressedKeys.put(convertedKeyCode, true);
                }
            }
            else {
                pressedKeys.put(convertedKeyCode, false);
            }

            for (int key : pressedKeys.keySet()) {
                if (pressedKeys.get(key)) {
                    canvas.keyRepeated(convertedKeyCode);
                }
                else {
                    canvas.keyPressed(convertedKeyCode);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gamePanel.getDisplayable() instanceof Canvas canvas) {
            int convertedKeyCode = KeyUtils.awtToDefault(e.getKeyCode());
            if (HangarState.getKeyboard() == Keyboards.Nokia) {
                convertedKeyCode = KeyUtils.defaultToNokia(convertedKeyCode);
            }
            pressedKeys.remove(convertedKeyCode);
            canvas.keyReleased(convertedKeyCode);
        }
    }
}