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

import things.HangarState;

import java.awt.event.KeyEvent;

public final class KeyUtils {
    // TODO: add conversion from one to another HangarKeyCodes
    public static int awtToMidletKeyCode(int keyCode) {
        var keyCodes = HangarState.getProfile().getMidletKeyCodes();
        switch (keyCode) {
            case KeyEvent.VK_UP: return keyCodes.getUp();
            case KeyEvent.VK_DOWN: return keyCodes.getDown();
            case KeyEvent.VK_LEFT: return keyCodes.getLeft();
            case KeyEvent.VK_RIGHT: return keyCodes.getRight();
            case KeyEvent.VK_ENTER: return keyCodes.getFire();
            case KeyEvent.VK_1: return keyCodes.getA();
            case KeyEvent.VK_2: return keyCodes.getB();
            case KeyEvent.VK_3: return keyCodes.getC();
            case KeyEvent.VK_4: return keyCodes.getD();
            case KeyEvent.VK_NUMPAD0: return keyCodes.getNum0();
            case KeyEvent.VK_NUMPAD1: return keyCodes.getNum1();
            case KeyEvent.VK_NUMPAD2: return keyCodes.getNum2();
            case KeyEvent.VK_NUMPAD3: return keyCodes.getNum3();
            case KeyEvent.VK_NUMPAD4: return keyCodes.getNum4();
            case KeyEvent.VK_NUMPAD5: return keyCodes.getNum5();
            case KeyEvent.VK_NUMPAD6: return keyCodes.getNum6();
            case KeyEvent.VK_NUMPAD7: return keyCodes.getNum7();
            case KeyEvent.VK_NUMPAD8: return keyCodes.getNum8();
            case KeyEvent.VK_NUMPAD9: return keyCodes.getNum9();
            case KeyEvent.VK_Q: return keyCodes.getStar();
            case KeyEvent.VK_W: return keyCodes.getPound();
            default: return keyCode;
        }
    }
}