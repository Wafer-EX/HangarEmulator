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

package things.utils;

import com.nokia.mid.ui.FullCanvas;

import javax.microedition.lcdui.Canvas;
import java.awt.event.KeyEvent;

public final class KeyCodeConverter {
    // TODO: check conversion

    public static int awtToDefault(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP: return Canvas.UP;
            case KeyEvent.VK_DOWN: return Canvas.DOWN;
            case KeyEvent.VK_LEFT: return Canvas.LEFT;
            case KeyEvent.VK_RIGHT: return Canvas.RIGHT;
            case KeyEvent.VK_ENTER: return Canvas.FIRE;
            case KeyEvent.VK_1: return Canvas.GAME_A;
            case KeyEvent.VK_2: return Canvas.GAME_B;
            case KeyEvent.VK_3: return Canvas.GAME_C;
            case KeyEvent.VK_4: return Canvas.GAME_D;
            case KeyEvent.VK_NUMPAD0: return Canvas.KEY_NUM0;
            case KeyEvent.VK_NUMPAD1: return Canvas.KEY_NUM1;
            case KeyEvent.VK_NUMPAD2: return Canvas.KEY_NUM2;
            case KeyEvent.VK_NUMPAD3: return Canvas.KEY_NUM3;
            case KeyEvent.VK_NUMPAD4: return Canvas.KEY_NUM4;
            case KeyEvent.VK_NUMPAD5: return Canvas.KEY_NUM5;
            case KeyEvent.VK_NUMPAD6: return Canvas.KEY_NUM6;
            case KeyEvent.VK_NUMPAD7: return Canvas.KEY_NUM7;
            case KeyEvent.VK_NUMPAD8: return Canvas.KEY_NUM8;
            case KeyEvent.VK_NUMPAD9: return Canvas.KEY_NUM9;
            case KeyEvent.VK_Q: return Canvas.KEY_STAR;
            case KeyEvent.VK_W: return Canvas.KEY_POUND;
            default: return keyCode;
        }
    }

    public static int defaultToNokia(int keyCode) {
        switch (keyCode) {
            case Canvas.UP: return FullCanvas.KEY_UP_ARROW;
            case Canvas.DOWN: return FullCanvas.KEY_DOWN_ARROW;
            case Canvas.LEFT: return FullCanvas.KEY_LEFT_ARROW;
            case Canvas.RIGHT: return FullCanvas.KEY_RIGHT_ARROW;
            case Canvas.FIRE: return FullCanvas.KEY_SOFTKEY3;
            case Canvas.GAME_A: return FullCanvas.KEY_SOFTKEY1;
            case Canvas.GAME_B: return FullCanvas.KEY_SOFTKEY2;
            default: return keyCode;
        }
    }
}